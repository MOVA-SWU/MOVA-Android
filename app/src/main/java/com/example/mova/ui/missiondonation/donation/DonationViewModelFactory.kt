package com.example.mova.ui.missiondonation.donation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mova.data.source.remote.repository.DonationRepository

class DonationViewModelFactory(
    private val repository: DonationRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DonationViewModel::class.java)) {
            return DonationViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}