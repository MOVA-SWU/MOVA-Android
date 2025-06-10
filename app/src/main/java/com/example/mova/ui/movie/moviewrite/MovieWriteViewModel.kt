package com.example.mova.ui.movie.moviewrite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.request.MovieWriteRequest
import com.example.mova.data.model.response.MovieWriteResponse
import com.example.mova.data.source.remote.repository.MovieWriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieWriteViewModel @Inject constructor(private val repository: MovieWriteRepository): ViewModel() {
    private val _movieWriteResponse = MutableStateFlow<Result<MovieWriteResponse>?>(null)
    val movieWriteResponse = _movieWriteResponse.asStateFlow()

    fun movieWrite(request: MovieWriteRequest) {
        viewModelScope.launch {
            _movieWriteResponse.value = repository.postMovieWrite(request)
        }
    }

    fun clearMovieWrite() {
        _movieWriteResponse.value = null
    }
}