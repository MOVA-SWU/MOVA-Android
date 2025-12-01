package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.response.MissionDetailResponse
import com.example.mova.data.model.response.MovieDetailResponse
import com.example.mova.data.model.response.VerificationResponse
import com.example.mova.data.source.remote.network.RetrofitService
import okhttp3.MultipartBody
import javax.inject.Inject

class MovieDetailRepository @Inject constructor(private val retrofitService: RetrofitService) {
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

    suspend fun patchMissionComplete(movieRecordId: Int, imagePart: MultipartBody.Part): Result<VerificationResponse> {
        return try {
            val response = retrofitService.patchMissionComplete(
                movieRecordId = movieRecordId,
                image = imagePart
            )

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(NoSuchElementException("서버 응답 본문이 비어 있습니다."))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(
                    Exception(
                        "API 오류 발생: HTTP ${response.code()} - $errorBody"
                    )
                )
            }
        } catch (e: Exception) {
            Result.failure(Exception("네트워크 오류 발생", e))
        }
    }
}