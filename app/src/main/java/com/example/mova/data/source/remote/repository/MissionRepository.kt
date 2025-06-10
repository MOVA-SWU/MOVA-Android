package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.response.MissionListResponse
import com.example.mova.data.model.response.PointSumResponse
import com.example.mova.data.source.remote.network.RetrofitService
import javax.inject.Inject

class MissionRepository @Inject constructor(private val retrofitService: RetrofitService) {
    suspend fun getMissionAvailable(): Result<List<MissionListResponse>> {
        return try {
            val response = retrofitService.getMissionAvailable("AVAILABLE")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getMissionComplete(): Result<List<MissionListResponse>> {
        return try {
            val response = retrofitService.getMissionComplete("COMPLETED")
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPointSum(): Result<PointSumResponse> {
        return try {
            val response = retrofitService.getPointSum()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}