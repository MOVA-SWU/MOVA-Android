package com.example.mova.ui.home

import com.example.mova.data.model.response.MovieListResponse

interface MovieClickListener {
    fun onMovieClick(movie: MovieListResponse)
}