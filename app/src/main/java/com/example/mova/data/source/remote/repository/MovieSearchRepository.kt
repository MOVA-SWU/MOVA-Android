package com.example.mova.data.source.remote.repository

import com.example.mova.BuildConfig
import com.example.mova.data.model.response.GenreMapper
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.data.source.remote.network.TMDBService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieSearchRepository(private val tmdbService: TMDBService) {
    suspend fun searchMovies(query: String): List<MovieInfo> {
        return withContext(Dispatchers.IO) {
            try {
                val response = tmdbService.searchMovies(apiKey = BuildConfig.TMDB_API_KEY, query = query)
                val genreMap = GenreMapper.getGenreMap()
                response.results.map { it.toMovie(genreMap) }
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}