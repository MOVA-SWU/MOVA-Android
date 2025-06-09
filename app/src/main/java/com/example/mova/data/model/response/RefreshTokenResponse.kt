package com.example.mova.data.model.response

data class RefreshTokenResponse(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)