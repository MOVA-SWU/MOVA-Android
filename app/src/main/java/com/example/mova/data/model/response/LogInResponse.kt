package com.example.mova.data.model.response

data class LogInResponse(
    val email: String,
    val token: Token
)

data class Token(
    val grantType: String,
    val accessToken: String,
    val refreshToken: String
)
