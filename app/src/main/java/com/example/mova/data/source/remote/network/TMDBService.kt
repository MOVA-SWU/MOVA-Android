package com.example.mova.data.source.remote.network

import com.example.mova.data.model.response.TMDBResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "ko-KR"
    ): TMDBResponse
}