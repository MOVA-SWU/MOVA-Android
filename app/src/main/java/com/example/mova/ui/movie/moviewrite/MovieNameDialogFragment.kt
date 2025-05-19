package com.example.mova.ui.movie.moviewrite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mova.databinding.DialogMovieNameBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieNameDialogFragment: DialogFragment() {

    private var _binding: DialogMovieNameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieWriteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMovieNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        setLayout()
    }

    private fun setLayout() {
        binding.btnDialogConfirm.setOnClickListener {
            val movieName = binding.etDialogMovieName.text.toString().trim()
            if (movieName.isNotEmpty()) {
                viewModel.searchMovies(movieName)
                observeMovieList()
            } else {
                binding.etDialogMovieName.error = "영화 제목을 입력하세요."
            }
        }

        binding.btnDialogCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun observeMovieList() {
        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.movieInfo.collectLatest { movieList ->
//                if (movieList.isNotEmpty()) {
//                    MovieSelectionDialogFragment().show(parentFragmentManager, "MovieSelectionDialog")
//                    dismiss()
//                }
//            }
            viewModel.movieInfo
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { movieList ->
                    if (movieList.isNotEmpty()) {
                        MovieSelectionDialogFragment().show(parentFragmentManager, "MovieSelectionDialog")
                        dismiss()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}