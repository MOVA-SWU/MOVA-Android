package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.request.MovieWriteRequest
import com.example.mova.data.model.response.MovieWriteResponse
import com.example.mova.data.source.remote.network.RetrofitService

class MovieWriteRepository(private val retrofitService: RetrofitService) {
    suspend fun postMovieWrite(request: MovieWriteRequest): Result<MovieWriteResponse> {
        return try {
            val response = retrofitService.postMovieWrite(request)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}