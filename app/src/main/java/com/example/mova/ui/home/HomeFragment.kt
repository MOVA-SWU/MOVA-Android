package com.example.mova.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mova.R
import com.example.mova.data.model.Banner
import com.example.mova.data.model.Movie
import com.example.mova.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val bannerAdapter = HomeBannerAdapter(object: MovieClickListener {
        override fun onMovieClick(movieId: String) {
            findNavController().navigate(R.id.action_home_to_movie_detail)
        }
    })

    private val movieAdapter = HomeMovieAdapter(object: MovieClickListener {
        override fun onMovieClick(movieId: String) {
            findNavController().navigate(R.id.action_home_to_movie_detail)
        }
    })

    val dummyBanners = listOf(
        Banner(1, "Banner 1", ""),
        Banner(2, "Banner 2", "")
    )
    val dummyBanners2 = listOf(
        Movie(1, ""),
        Movie(2, ""),
        Movie(3, ""),
        Movie(4, ""),
        Movie(5, "")
    )

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
        binding.tvHomeMovieNull.isVisible = dummyBanners.isEmpty() && dummyBanners2.isEmpty()
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
            }

            TabLayoutMediator(
                binding.viewpagerHomeBannerIndicator, this) { tab, position ->
            }.attach()
        }

        bannerAdapter.submitList(dummyBanners)
    }

    private fun setMovieList() {
        binding.rvHomeMovieList.adapter = movieAdapter
        movieAdapter.submitList(dummyBanners2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}