package com.example.mova.ui.missiondonation.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.MissionListResponse
import com.example.mova.data.model.response.PointSumResponse
import com.example.mova.data.source.remote.repository.MissionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MissionViewModel(private val repository: MissionRepository): ViewModel() {
    private val _missionListResponse = MutableStateFlow(Result.success(emptyList<MissionListResponse>()))
    val missionListResponse = _missionListResponse.asStateFlow()

    private val _pointSumResponse = MutableStateFlow<Result<PointSumResponse>?>(null)
    val pointSumResponse = _pointSumResponse.asStateFlow()

    fun loadMissionAvailableList() {
        viewModelScope.launch {
            _missionListResponse.value = repository.getMissionAvailable()
        }
    }

    fun loadMissionCompleteList() {
        viewModelScope.launch {
            _missionListResponse.value = repository.getMissionComplete()
        }
    }

    fun loadPointSum() {
        viewModelScope.launch {
            _pointSumResponse.value = repository.getPointSum()
        }
    }
}