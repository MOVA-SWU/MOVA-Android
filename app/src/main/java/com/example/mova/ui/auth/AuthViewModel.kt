package com.example.mova.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mova.data.model.request.EmailCheckRequest
import com.example.mova.data.model.request.LogInRequest
import com.example.mova.data.model.request.SignUpRequest
import com.example.mova.data.model.response.EmailCheckResponse
import com.example.mova.data.model.response.LogInResponse
import com.example.mova.data.model.response.SignUpResponse
import com.example.mova.data.source.remote.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {
    private val _signUpResponse = MutableStateFlow<Result<SignUpResponse>?>(null)
    val signUpResponse = _signUpResponse.asStateFlow()

    private val _emailCheckResponse = MutableStateFlow<Result<EmailCheckResponse>?>(null)
    val emailCheckResponse = _emailCheckResponse.asStateFlow()

    private val _loginResponse = MutableStateFlow<Result<LogInResponse>?>(null)
    val loginResponse = _loginResponse.asStateFlow()

    fun signUp(request: SignUpRequest) {
        viewModelScope.launch {
            _signUpResponse.value = repository.postSignUp(request)
        }
    }

    fun clearSignUp() {
        _signUpResponse.value = null
    }

    fun emailCheck(request: EmailCheckRequest) {
        viewModelScope.launch {
            _emailCheckResponse.value = repository.postEmailCheck(request)
        }
    }

    fun clearEmailCheck() {
        _emailCheckResponse.value = null
    }

    fun logIn(request: LogInRequest) {
        viewModelScope.launch {
            _loginResponse.value = repository.postLogIn(request)
        }
    }

    fun clearLogIn() {
        _loginResponse.value = null
    }
}