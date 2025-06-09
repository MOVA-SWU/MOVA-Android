package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.request.MissionCompleteRequest
import com.example.mova.data.model.response.MissionDetailResponse
import com.example.mova.data.model.response.MovieDetailResponse
import com.example.mova.data.source.remote.network.RetrofitService

class MovieDetailRepository(private val retrofitService: RetrofitService) {
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetailResponse> {
        return try {
            val response = retrofitService.getMovieDetail(movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMissionDetail(movieId: Int): Result<MissionDetailResponse> {
        return try {
            val response = retrofitService.getMissionDetail(movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun patchMissionComplete(movieId: Int, missionId: Int): Result<Unit> {
        return try {
            val response = retrofitService.patchMissionComplete(movieId, missionId, MissionCompleteRequest())
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("API 실패: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}