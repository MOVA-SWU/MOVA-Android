package com.example.mova.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyListResponse(
    val companyId: Int,
    val name: String
): Parcelable