package com.example.mova.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.data.model.response.MovieListResponse
import com.example.mova.data.source.remote.network.RetrofitClient
import com.example.mova.data.source.remote.repository.HomeRepository
import com.example.mova.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val bannerAdapter = HomeBannerAdapter(object: MovieClickListener {
        override fun onMovieClick(movie: MovieListResponse) {
            val action = HomeFragmentDirections.actionHomeToMovieDetail(movie = movie)
            findNavController().navigate(action)
        }
    })

    private val movieAdapter = HomeMovieAdapter(object: MovieClickListener {
        override fun onMovieClick(movie: MovieListResponse) {
            val action = HomeFragmentDirections.actionHomeToMovieDetail(movie = movie)
            findNavController().navigate(action)
        }
    })

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(HomeRepository(RetrofitClient.retrofitService))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        setAdapter()
        binding.btnHomeMovieAdd.setOnClickListener {
            findNavController().navigate(R.id.action_home_to_movie_write)
        }
    }

    private fun setAdapter() {
        setBanners()
        setMovieList()
    }

    private fun setBanners() {
        with(binding.viewpagerHomeBanner) {
            adapter = bannerAdapter
            offscreenPageLimit = 2

            val displayMetrics = resources.displayMetrics
            val screenWidth = displayMetrics.widthPixels

            val pageMarginPx = (16 * displayMetrics.density).toInt()
            val pageOffsetPx = (screenWidth - pageMarginPx * 2) / 10

            setPageTransformer { page, position ->
                val offset = position * -(10 * pageOffsetPx + pageMarginPx)
                page.translationX = offset
                page.scaleY = 0.9f + (1 - abs(position)) * 0.1f
                page.translationZ = -abs(position)
            }

            TabLayoutMediator(
                binding.viewpagerHomeBannerIndicator, this) { tab, position ->
            }.attach()
        }
    }

    private fun setMovieList() {
        binding.rvHomeMovieList.adapter = movieAdapter
        viewModel.loadMovieList()
        viewModel.loadLatestMovie()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                var currentMovieList: List<MovieListResponse>? = null
                var currentBannerList: List<MovieListResponse>? = null

                launch {
                    viewModel.movieListResponse.collectLatest { result ->
                        val list = result.getOrNull()
                        currentMovieList = list
                        movieAdapter.submitList(list)
                        updateMovieNullVisibility(currentMovieList, currentBannerList)
                    }
                }
                launch {
                    viewModel.latestMovieResponse.collectLatest { result ->
                        val list = result.getOrNull()
                        currentBannerList = list
                        bannerAdapter.submitList(list)
                        updateMovieNullVisibility(currentMovieList, currentBannerList)
                    }
                }
            }
        }
    }

    private fun updateMovieNullVisibility(
        movieList: List<MovieListResponse>?,
        bannerList: List<MovieListResponse>?
    ) {
        val isMovieListEmpty = movieList.isNullOrEmpty()
        val isBannerListEmpty = bannerList.isNullOrEmpty()

        binding.tvHomeMovieNull.isVisible = isMovieListEmpty && isBannerListEmpty
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}