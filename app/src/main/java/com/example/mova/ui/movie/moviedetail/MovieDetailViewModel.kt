package com.example.mova.ui.movie.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MissionDetailResponse
import com.example.mova.data.model.response.MovieDetailResponse
import com.example.mova.data.source.remote.repository.MovieDetailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(private val repository: MovieDetailRepository): ViewModel() {
    private val _movieDetailResponse = MutableStateFlow<Result<MovieDetailResponse>?>(null)
    val movieDetailResponse = _movieDetailResponse.asStateFlow()

    private val _missionDetailResponse = MutableStateFlow<Result<MissionDetailResponse>?>(null)
    val missionDetailResponse = _missionDetailResponse.asStateFlow()

    private val _missionCompleteResponse = MutableStateFlow<Result<Unit>?>(null)
    val missionCompleteResponse = _missionCompleteResponse.asStateFlow()

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _movieDetailResponse.value = repository.getMovieDetail(movieId)
        }
    }

    fun loadMissionDetail(movieId: Int) {
        viewModelScope.launch {
            _missionDetailResponse.value = repository.getMissionDetail(movieId)
        }
    }

    fun missionComplete(movieId: Int, missionId: Int) {
        viewModelScope.launch {
            _missionCompleteResponse.value = repository.patchMissionComplete(movieId, missionId)
        }
    }
}