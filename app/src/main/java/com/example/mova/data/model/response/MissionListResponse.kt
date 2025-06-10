package com.example.mova.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MissionListResponse(
    val myMissionId: Int,
    val movieRecordId: Int,
    val mission: String,
    val cost: Int
): Parcelable