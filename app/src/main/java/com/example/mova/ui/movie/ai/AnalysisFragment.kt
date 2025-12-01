package com.example.mova.ui.movie.ai

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mova.databinding.FragmentAnalysisBinding
import com.example.mova.ui.movie.moviedetail.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AnalysisFragment : Fragment() {

    private var _binding: FragmentAnalysisBinding? = null
    private val binding get() = _binding!!

    private val args: AnalysisFragmentArgs by navArgs()
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnalysisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAnalysisBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val movieId = args.movieId
        val missionId = args.missionId
        val photoUri = args.photoUri

        if (movieId != 0 && missionId != 0 && photoUri.isNotEmpty()) {
            val uri = Uri.parse(photoUri)
            viewModel.missionComplete(movieId, uri)

            setMissionCompleteObserver()
        } else {
            Toast.makeText(context, "필수 정보가 부족하여 인증을 시작할 수 없습니다.", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
        }
    }

    private fun setMissionCompleteObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.missionCompleteResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        findNavController().navigateUp()

                        if (it.isSuccess) {
                            val response = it.getOrNull()
                            if (response?.result == "성공") {
                                Toast.makeText(context, "미션 성공", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "미션 실패", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Toast.makeText(context, "사진 검증 실패", Toast.LENGTH_LONG).show()
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