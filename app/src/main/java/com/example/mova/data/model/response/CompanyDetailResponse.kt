package com.example.mova.data.model.response

data class CompanyDetailResponse(
    val bannerImage: String,
    val explainText: String,
    val productionImages:List<String>,
    val supportCost: Int
)