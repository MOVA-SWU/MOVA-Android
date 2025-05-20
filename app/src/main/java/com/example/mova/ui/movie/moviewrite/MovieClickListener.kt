package com.example.mova.ui.movie.moviewrite

import com.example.mova.data.model.response.MovieInfo

interface MovieClickListener {
    fun onMovieClick(movie: MovieInfo)
}