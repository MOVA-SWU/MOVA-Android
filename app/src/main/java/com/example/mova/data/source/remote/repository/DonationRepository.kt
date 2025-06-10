package com.example.mova.data.source.remote.repository

import com.example.mova.data.model.response.CompanyDetailResponse
import com.example.mova.data.model.response.CompanyListResponse
import com.example.mova.data.model.request.DonationCompleteRequest
import com.example.mova.data.source.remote.network.RetrofitService

class DonationRepository(private val retrofitService: RetrofitService) {
    suspend fun getCompanyList(): Result<List<CompanyListResponse>> {
        return try {
            val response = retrofitService.getCompanyList()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCompanyDetail(companyId: Int): Result<CompanyDetailResponse> {
        return try {
            val response = retrofitService.getCompanyDetail(companyId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun putDonationComplete(companyId: Int): Result<Unit> {
        return try {
            val response = retrofitService.putDonationComplete(companyId, DonationCompleteRequest())
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