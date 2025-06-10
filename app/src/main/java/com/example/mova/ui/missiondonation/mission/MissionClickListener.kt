package com.example.mova.ui.missiondonation.mission

import com.example.mova.data.model.response.MissionListResponse

interface MissionClickListener {
    fun onMissionClick(mission: MissionListResponse)
}