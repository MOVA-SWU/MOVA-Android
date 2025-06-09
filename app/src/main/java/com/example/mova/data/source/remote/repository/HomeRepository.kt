package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.response.MovieListResponse
import com.example.mova.data.source.remote.network.RetrofitService

class HomeRepository(private val retrofitService: RetrofitService) {
    suspend fun getMovieList(): Result<List<MovieListResponse>> {
        return try {
            val response = retrofitService.getMovieList()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLatestMovie(): Result<List<MovieListResponse>> {
        return try {
            val response = retrofitService.getLatestMovie()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}