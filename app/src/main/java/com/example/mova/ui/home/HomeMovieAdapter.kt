package com.example.mova.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.data.model.response.MovieListResponse
import com.example.mova.databinding.ItemHomeMovieListBinding
import com.example.mova.ui.extensions.load

class HomeMovieAdapter(private val clickListener: MovieClickListener) : ListAdapter<MovieListResponse, HomeMovieAdapter.HomeMovieViewHolder>(
    MovieDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieViewHolder {
        return HomeMovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class HomeMovieViewHolder private constructor(private val binding: ItemHomeMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieListResponse, clickListener: MovieClickListener) {
            binding.ivHomeMovieList.load(movie.imageUrl)
            binding.ivHomeMovieList.setOnClickListener {
                clickListener.onMovieClick(movie)
            }
        }

        companion object {
            fun from(parent: ViewGroup): HomeMovieViewHolder {
                return HomeMovieViewHolder(
                    ItemHomeMovieListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}

class MovieDiffCallback: DiffUtil.ItemCallback<MovieListResponse>() {

    override fun areItemsTheSame(oldItem: MovieListResponse, newItem: MovieListResponse): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: MovieListResponse, newItem: MovieListResponse): Boolean {
        return oldItem == newItem
    }
}