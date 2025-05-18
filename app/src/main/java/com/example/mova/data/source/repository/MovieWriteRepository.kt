package com.example.mova.data.source.repository

import com.example.mova.BuildConfig
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.data.source.network.TMDBClient

class MovieWriteRepository {
    suspend fun searchMovies(query: String): List<MovieInfo>? {
        return try {
            val response = TMDBClient.api.searchMovies(apiKey = BuildConfig.TMDB_API_KEY, query = query)
            response.results.ifEmpty { emptyList() }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}