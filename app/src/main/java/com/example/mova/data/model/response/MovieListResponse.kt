package com.example.mova.data.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieListResponse(
    val movieId: Int,
    val imageUrl: String
): Parcelable
