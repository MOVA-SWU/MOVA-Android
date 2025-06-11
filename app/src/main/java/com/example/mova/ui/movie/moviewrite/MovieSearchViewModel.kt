package com.example.mova.ui.movie.moviewrite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.data.source.remote.repository.MovieSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieSearchViewModel @Inject constructor(private val repository : MovieSearchRepository) : ViewModel() {

    private val _movieList = MutableStateFlow<List<MovieInfo>>(emptyList())
    val movieInfo = _movieList.asStateFlow()

    fun searchMovies(query: String) {
        viewModelScope.launch {
            _movieList.emit(emptyList())
            val result = repository.searchMovies(query)
            _movieList.emit(result)
        }
    }
}