package com.example.mova.data.model.response

data class MovieDetailResponse(
    val movieId: Int,
    val title: String,
    val rating: Double,
    val dateTime: String,
    val content: String,
    val imageUrl: String
)