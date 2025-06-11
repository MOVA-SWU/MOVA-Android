package com.example.mova.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.response.CharacterCollectResponse
import com.example.mova.data.model.response.PointSumResponse
import com.example.mova.data.model.response.ProfileResponse
import com.example.mova.data.source.remote.repository.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val repository: MyPageRepository): ViewModel() {
    private val _profileResponse = MutableStateFlow<Result<ProfileResponse>?>(null)
    val profileResponse = _profileResponse.asStateFlow()

    private val _logoutResponse = MutableStateFlow<Result<Unit>?>(null)
    val logoutResponse = _logoutResponse.asStateFlow()

    private val _pointSumResponse = MutableStateFlow<Result<PointSumResponse>?>(null)
    val pointSumResponse = _pointSumResponse.asStateFlow()

    private val _nicknameResponse = MutableStateFlow<Result<Unit>?>(null)
    val nicknameResponse = _nicknameResponse.asStateFlow()

    private val _characterCollectResponse = MutableStateFlow<Result<CharacterCollectResponse>?>(null)
    val characterCollectResponse = _characterCollectResponse.asStateFlow()

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

    fun loadPointSum() {
        viewModelScope.launch {
            _pointSumResponse.value = repository.getPointSum()
        }
    }

    fun changeNickname(nickname: String) {
        viewModelScope.launch {
            val result = repository.patchNickname(nickname)
            _nicknameResponse.value = result

            if (result.isSuccess) {
                loadProfile()
            }
        }
    }

    fun loadCharacterCollect() {
        viewModelScope.launch {
            _characterCollectResponse.value = repository.getCharacterCollect()
        }
    }
}