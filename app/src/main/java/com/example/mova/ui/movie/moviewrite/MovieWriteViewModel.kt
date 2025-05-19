package com.example.mova.ui.movie.moviewrite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.data.source.network.RetrofitClient
import com.example.mova.data.source.repository.MovieWriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieWriteViewModel(
    private val repository : MovieWriteRepository = MovieWriteRepository(RetrofitClient.tmdbService)
) : ViewModel() {

    private val _movieList = MutableStateFlow<List<MovieInfo>>(emptyList())
    val movieInfo = _movieList.asStateFlow()

    fun searchMovies(query: String) {
        viewModelScope.launch {
            repository.searchMovies(query).let {
                _movieList.value = it
            }
        }
    }
}