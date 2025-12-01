package com.example.mova.data.model.response

data class MissionDetailResponse(
    val myMissionId: Int,
    val mission: String,
    val cost: Int,
    val characterImage: String,
    val checkedUrl: String?,
    val missionStatus: String
)