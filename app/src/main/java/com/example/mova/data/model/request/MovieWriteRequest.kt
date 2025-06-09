package com.example.mova.data.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieWriteRequest(
    val title: String,
    val rating: Double,
    val dateTime: String,
    val content: String,
    val imageUrl: String
): Parcelable