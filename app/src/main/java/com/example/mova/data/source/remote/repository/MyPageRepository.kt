package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.request.NicknameRequest
import com.example.mova.data.model.response.CharacterCollectResponse
import com.example.mova.data.model.response.PointSumResponse
import com.example.mova.data.model.response.ProfileResponse
import com.example.mova.data.source.remote.network.RetrofitService

class MyPageRepository(private val retrofitService: RetrofitService) {
    suspend fun getProfile(): Result<ProfileResponse> {
        return try {
            val response = retrofitService.getProfile()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun postLogout(): Result<Unit> {
        return try {
            val response = retrofitService.postLogout()
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Logout failed with code ${response.code()}"))
            }
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

    suspend fun patchNickname(nickname: String): Result<Unit> {
        return try {
            val response = retrofitService.patchNickname(NicknameRequest(nickname))
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("닉네임 수정 실패:  ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCharacterCollect(): Result<CharacterCollectResponse> {
        return try {
            val response = retrofitService.getCharacterCollect()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}