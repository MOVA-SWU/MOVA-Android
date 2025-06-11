package com.example.mova.ui.movie.ai

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
import com.example.mova.R
import com.example.mova.databinding.FragmentLoadingBinding
import com.example.mova.ui.movie.moviewrite.MovieWriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingFragment : Fragment() {

    private var _binding : FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    private val args: LoadingFragmentArgs by navArgs()

    private val viewModel: MovieWriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setLayout()
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.movieWrite(args.movieWriteRequest)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieWriteResponse
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collectLatest { result ->
                    result?.let {
                        if (it.isSuccess) {
                            val response = it.getOrNull()
                            response?.let { movieWriteResponse ->
                                val action =
                                    LoadingFragmentDirections.actionLoadingToAi(movieWriteResponse)
                                findNavController().navigate(action)
                            }
                        } else {
                            findNavController().navigateUp()
                            Toast.makeText(context, "영화 기록 실패", Toast.LENGTH_SHORT).show()
                        }
                        viewModel.clearMovieWrite()
                    }
                }
        }
    }

    private fun setLayout() {
        binding.btnLoadingBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.ivLoading.setOnClickListener {
            findNavController().navigate(R.id.action_loading_to_ai)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}