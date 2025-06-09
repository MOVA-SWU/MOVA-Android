package com.example.mova.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieWriteResponse(
    val movie: String,
    val mission: String,
    val effect: String,
    val point_message: String,
    val point: Int,
    val theme: String,
    val image_url: String
): Parcelable