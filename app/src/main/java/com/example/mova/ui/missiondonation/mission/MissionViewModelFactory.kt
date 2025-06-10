package com.example.mova.ui.missiondonation.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mova.data.source.remote.repository.MissionRepository

class MissionViewModelFactory(
    private val repository: MissionRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MissionViewModel::class.java)) {
            return MissionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}