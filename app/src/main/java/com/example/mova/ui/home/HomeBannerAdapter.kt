package com.example.mova.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.data.model.response.MovieListResponse
import com.example.mova.databinding.ItemHomeBannerBinding
import com.example.mova.ui.extensions.load

class HomeBannerAdapter(private val clickListener: MovieClickListener) : ListAdapter<MovieListResponse, HomeBannerAdapter.HomeMovieViewHolder>(
    BannerDiffCallback()
){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMovieViewHolder {
        return HomeMovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomeMovieViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class HomeMovieViewHolder private constructor(private val binding: ItemHomeBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieListResponse, clickListener: MovieClickListener) {
            binding.ivBannerImage.load(movie.imageUrl)
            binding.ivBannerImage.setOnClickListener {
                clickListener.onMovieClick(movie)
            }
        }

        companion object {
            fun from(parent: ViewGroup): HomeMovieViewHolder {
                return HomeMovieViewHolder(
                    ItemHomeBannerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}

class BannerDiffCallback: DiffUtil.ItemCallback<MovieListResponse>() {

    override fun areItemsTheSame(oldItem: MovieListResponse, newItem: MovieListResponse): Boolean {
        return oldItem.movieId == newItem.movieId
    }

    override fun areContentsTheSame(oldItem: MovieListResponse, newItem: MovieListResponse): Boolean {
        return oldItem == newItem
    }
}