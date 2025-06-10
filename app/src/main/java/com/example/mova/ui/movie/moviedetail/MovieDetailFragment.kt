package com.example.mova.ui.movie.moviedetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mova.R
import com.example.mova.data.source.remote.network.RetrofitClient
import com.example.mova.data.source.remote.repository.MovieDetailRepository
import com.example.mova.databinding.FragmentMovieDetailBinding
import com.example.mova.ui.extensions.load
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieDetailFragment: Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModelFactory(MovieDetailRepository(RetrofitClient.retrofitService))
    }

    private var missionId: Int? = null
    private var movieId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
    }

    private fun setLayout() {
        setViewModel()
        binding.btnMovieDetailBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnMovieDetailPoint.setOnClickListener {
            patchMissionComplete()
        }
    }

    private fun setViewModel() {
        movieId = args.movie?.movieId ?: args.mission?.movieRecordId
        movieId?.let {
            viewModel.loadMovieDetail(it)
            viewModel.loadMissionDetail(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.movieDetailResponse.collectLatest { result ->
                        result?.let {
                            if (it.isSuccess) {
                                with(binding) {
                                    val response = it.getOrNull()
                                    ivMovieDetailPoster.load(response?.imageUrl)
                                    tvMovieDetailNameField.text = response?.title
                                    ratingbarMovieDetailRating.rating = response?.rating?.toFloat() ?: 0f
                                    tvMovieDetailDateField.text = response?.dateTime
                                    tvMovieDetailContent.text = response?.content
                                }
                            } else {
                                Toast.makeText(context, "기록 불러오기 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                launch {
                    viewModel.missionDetailResponse.collectLatest { result ->
                        result?.let {
                            if (it.isSuccess) {
                                with(binding) {
                                    val response = it.getOrNull()
                                    tvMovieDetailAiMissionField.text = response?.mission
                                    btnMovieDetailPoint.text = "${response?.cost} P"
                                    ivMovieDetailCharacter.load(response?.characterImage)
                                    missionId = response?.missionId
                                    if (response?.missionStatus == "COMPLETED") {
                                        with(binding.btnMovieDetailPoint) {
                                            isEnabled = false
                                            setBackgroundResource(R.drawable.background_gray55_60)
                                        }
                                        binding.ivMovieDetailCharacterLock.visibility = View.GONE
                                        binding.ivMovieDetailCharacter.alpha = 1f
                                    } else {
                                        with(binding.btnMovieDetailPoint) {
                                            setBackgroundResource(R.drawable.background_primary_60)
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(context, "미션 불러오기 실패", Toast.LENGTH_SHORT).show()
                            }

                        }
                    }
                }
            }
        }
    }

    private fun patchMissionComplete() {
        if (movieId != null && missionId != null) {
            viewModel.missionComplete(movieId!!, missionId!!)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.missionCompleteResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            with(binding.btnMovieDetailPoint) {
                                isEnabled = false
                                setBackgroundResource(R.drawable.background_gray55_60)
                                setTextAppearance(R.style.InterMedium_White100_S10)
                            }
                            binding.ivMovieDetailCharacterLock.visibility = View.GONE
                            binding.ivMovieDetailCharacter.alpha = 1f
                        } else {
                            Toast.makeText(context, "미션 수행 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}