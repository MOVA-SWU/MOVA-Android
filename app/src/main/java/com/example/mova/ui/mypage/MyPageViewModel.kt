package com.example.mova.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.ProfileResponse
import com.example.mova.data.source.remote.repository.MyPageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyPageViewModel(private val repository: MyPageRepository): ViewModel() {
    private val _profileResponse = MutableStateFlow<Result<ProfileResponse>?>(null)
    val profileResponse = _profileResponse.asStateFlow()

    private val _logoutResponse = MutableStateFlow<Result<Unit>?>(null)
    val logoutResponse = _logoutResponse.asStateFlow()

    fun loadProfile() {
        viewModelScope.launch {
            _profileResponse.value = repository.getProfile()
        }
    }

    fun clearProfile() {
        _profileResponse.value = null
    }

    fun logout() {
        viewModelScope.launch {
            _logoutResponse.value = repository.postLogout()
        }
    }

    fun clearLogout() {
        _logoutResponse.value = null
    }
}