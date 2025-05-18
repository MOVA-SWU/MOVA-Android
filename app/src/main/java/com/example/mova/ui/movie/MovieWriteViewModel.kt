package com.example.mova.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.data.source.repository.MovieWriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieWriteViewModel : ViewModel() {
    private val repository = MovieWriteRepository()

    private val _movieInfo = MutableStateFlow<List<MovieInfo>>(emptyList())
    val movieInfo = _movieInfo.asStateFlow()

    fun searchMovies(query: String) {
        viewModelScope.launch {
            repository.searchMovies(query)?.let {
                _movieInfo.value = it
            }
        }
    }
}