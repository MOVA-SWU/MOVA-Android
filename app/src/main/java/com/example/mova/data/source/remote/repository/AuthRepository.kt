package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.request.EmailCheckRequest
import com.example.mova.data.model.request.LogInRequest
import com.example.mova.data.model.request.SignUpRequest
import com.example.mova.data.model.response.EmailCheckResponse
import com.example.mova.data.model.response.LogInResponse
import com.example.mova.data.model.response.SignUpResponse
import com.example.mova.data.source.remote.network.RetrofitService

class AuthRepository(private val retrofitService: RetrofitService) {
    suspend fun postSignUp(request: SignUpRequest): Result<SignUpResponse> {
        return try {
            val response = retrofitService.postSignUp(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun postEmailCheck(request: EmailCheckRequest): Result<EmailCheckResponse> {
        return try {
            val response = retrofitService.postEmailCheck(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun postLogIn(request: LogInRequest): Result<LogInResponse> {
        return try {
            val response = retrofitService.postLogin(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}