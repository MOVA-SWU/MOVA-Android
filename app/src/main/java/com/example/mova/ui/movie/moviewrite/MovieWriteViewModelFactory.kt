package com.example.mova.ui.movie.moviewrite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mova.data.source.remote.repository.MovieWriteRepository

class MovieWriteViewModelFactory(
    private val repository: MovieWriteRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieWriteViewModel::class.java)) {
            return MovieWriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}