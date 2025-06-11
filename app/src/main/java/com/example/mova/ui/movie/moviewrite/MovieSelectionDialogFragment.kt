package com.example.mova.ui.movie.moviewrite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.databinding.DialogMovieSelectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieSelectionDialogFragment: DialogFragment() {

    private var _binding: DialogMovieSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieSearchViewModel by activityViewModels()

    private val adapter = MovieSelectionAdapter(object : MovieClickListener {
        override fun onMovieClick(movie: MovieInfo) {
            parentFragmentManager.setFragmentResult(
                "movieSelection",
                bundleOf(
                    "movieTitle" to movie.title,
                    "moviePosterUrl" to movie.posterUrl
                )
            )
            dismiss()
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogMovieSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        setAdapter()
    }

    private fun setAdapter() {
        binding.rvMovieSelection.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movieInfo
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { movieList ->
                    adapter.submitList(movieList)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}