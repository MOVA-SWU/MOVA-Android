package com.example.mova.ui.missiondonation.donation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.CompanyDetailResponse
import com.example.mova.data.model.response.CompanyListResponse
import com.example.mova.data.source.remote.repository.DonationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DonationViewModel @Inject constructor(private val repository: DonationRepository): ViewModel() {
    private val _companyListResponse = MutableStateFlow(Result.success(emptyList<CompanyListResponse>()))
    val companyListResponse = _companyListResponse.asStateFlow()

    private val _companyDetailResponse = MutableStateFlow<Result<CompanyDetailResponse>?>(null)
    val companyDetailResponse = _companyDetailResponse.asStateFlow()

    private val _donationCompleteResponse = MutableStateFlow<Result<Unit>?>(null)
    val donationCompleteResponse = _donationCompleteResponse.asStateFlow()

    fun loadMovieList() {
        viewModelScope.launch {
            _companyListResponse.value = repository.getCompanyList()
        }
    }

    fun loadCompanyDetail(companyId: Int) {
        viewModelScope.launch {
            _companyDetailResponse.value = repository.getCompanyDetail(companyId)
        }
    }

    fun donationComplete(companyId: Int) {
        viewModelScope.launch {
            _donationCompleteResponse.value = repository.putDonationComplete(companyId)
        }
    }
}