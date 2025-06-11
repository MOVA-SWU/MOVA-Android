package com.example.mova.ui.missiondonation.donation

import com.example.mova.data.model.response.CompanyListResponse

interface CompanyClickListener {
    fun onCompanyClick(company: CompanyListResponse)
}