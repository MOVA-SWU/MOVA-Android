package com.example.mova.data.source.remote.network

import com.example.mova.data.model.request.EmailCheckRequest
import com.example.mova.data.model.request.LogInRequest
import com.example.mova.data.model.request.SignUpRequest
import com.example.mova.data.model.response.EmailCheckResponse
import com.example.mova.data.model.response.LogInResponse
import com.example.mova.data.model.response.ProfileResponse
import com.example.mova.data.model.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @POST("/users/signup")
    suspend fun postSignUp(
        @Body signUpRequest: SignUpRequest
    ): SignUpResponse

    @POST("/users/emailCheck")
    suspend fun postEmailCheck(
        @Body emailCheckRequest: EmailCheckRequest
    ): EmailCheckResponse

    @POST("/users/login")
    suspend fun postLogin(
        @Body loginRequest: LogInRequest
    ): LogInResponse

    @POST("/users/logout")
    suspend fun postLogout(): Response<Unit>

    @GET("/myPage")
    suspend fun getProfile(): ProfileResponse
}