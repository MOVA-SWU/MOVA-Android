package com.example.mova.data.model.response

data class TMDBResponse(
    val results: List<MovieInfo>
)

data class MovieInfo(
    val title: String,
    val poster_path: String?
)