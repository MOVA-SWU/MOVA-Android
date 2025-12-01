package com.example.mova.ui.movie.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mova.R
import com.example.mova.databinding.FragmentMovieDetailBinding
import com.example.mova.ui.extensions.dpToPxExtensions
import com.example.mova.ui.extensions.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment: Fragment() {
    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by viewModels()

    private var missionId: Int? = null
    private var movieId: Int? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            val action = MovieDetailFragmentDirections.actionMovieDetailToAnalysis(
                movieId = movieId ?: 0,
                missionId = missionId ?: 0,
                photoUri = uri.toString()
            )
            findNavController().navigate(action)
        } else {
            Toast.makeText(context, "이미지 로드 실패", Toast.LENGTH_SHORT).show()
        }
    }

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
        movieId = args.movie?.movieId ?: args.mission?.movieRecordId
    }

    override fun onResume() {
        super.onResume()
        movieId?.let {
            viewModel.loadMovieDetail(it)
            viewModel.loadMissionDetail(it)
        }
    }

    private fun setLayout() {
        setViewModel()
        binding.btnMovieDetailBack.setOnClickListener {
            findNavController().previousBackStackEntry
                ?.savedStateHandle
                ?.set("mission_completed", true)
            findNavController().navigateUp()
        }
        binding.btnMovieDetailPoint.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun setViewModel() {
//        movieId = args.movie?.movieId ?: args.mission?.movieRecordId
//        movieId?.let {
//            viewModel.loadMovieDetail(it)
//            viewModel.loadMissionDetail(it)
//        }

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
                                    ivMovieDetailCharacter.load(response?.characterImage)
                                    missionId = response?.myMissionId
                                    if (response?.missionStatus == "COMPLETED") {
                                        with(binding.btnMovieDetailPoint) {
                                            isEnabled = false
                                            setBackgroundResource(R.drawable.background_gray55_60)
                                            btnMovieDetailPoint.text = "${response?.cost} P"
                                        }
                                        binding.ivMovieDetailImage.visibility = View.VISIBLE
                                        binding.ivMovieDetailImage.load(response.checkedUrl)
                                        binding.ivMovieDetailCharacterLock.visibility = View.GONE
                                        binding.ivMovieDetailCharacter.alpha = 1f

                                        val newPaddingEnd = requireContext().dpToPxExtensions(130)
                                        tvMovieDetailAiMissionField.setPaddingRelative(
                                            tvMovieDetailAiMissionField.paddingStart,
                                            tvMovieDetailAiMissionField.paddingTop,
                                            newPaddingEnd,
                                            tvMovieDetailAiMissionField.paddingBottom
                                        )
                                    } else {
                                        with(binding.btnMovieDetailPoint) {
                                            setBackgroundResource(R.drawable.background_primary_60)
                                            btnMovieDetailPoint.text = "사진 인증하고 ${response?.cost}P 받기"
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


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}