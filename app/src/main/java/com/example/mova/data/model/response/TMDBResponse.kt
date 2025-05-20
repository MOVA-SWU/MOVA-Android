package com.example.mova.data.model.response

import android.os.Parcelable
import com.example.mova.BuildConfig
import kotlinx.parcelize.Parcelize

data class TMDBResponse(
    val results: List<MovieItem>
)

data class MovieItem(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val genre_ids: List<Int>
) {
    fun toMovie(genreMap: Map<Int, String>): MovieInfo {
        return MovieInfo(
            id = id,
            title = title,
            posterUrl = poster_path?.let { "${BuildConfig.TMDB_POSTER_BASE_URL}$it" } ?: "",
            genres = genre_ids.mapNotNull { genreMap[it] }
        )
    }
}

@Parcelize
data class MovieInfo(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val genres: List<String>
): Parcelable