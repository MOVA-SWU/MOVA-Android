package com.example.mova.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MovieListResponse
import com.example.mova.data.source.remote.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository): ViewModel() {
    private val _movieListResponse = MutableStateFlow(Result.success(emptyList<MovieListResponse>()))
    val movieListResponse = _movieListResponse.asStateFlow()

    private val _latestMovieResponse = MutableStateFlow(Result.success(emptyList<MovieListResponse>()))
    val latestMovieResponse = _latestMovieResponse.asStateFlow()

    fun loadMovieList() {
        viewModelScope.launch {
            _movieListResponse.value = repository.getMovieList()
        }
    }

    fun loadLatestMovie() {
        viewModelScope.launch {
            _latestMovieResponse.value = repository.getLatestMovie()
        }
    }
}