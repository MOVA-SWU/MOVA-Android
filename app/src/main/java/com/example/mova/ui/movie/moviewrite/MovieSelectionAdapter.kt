package com.example.mova.ui.movie.moviewrite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.data.model.response.MovieInfo
import com.example.mova.databinding.ItemMovieSelectionBinding
import com.example.mova.ui.extensions.load

class MovieSelectionAdapter(
    private val clickListener: MovieClickListener
) : ListAdapter<MovieInfo, MovieSelectionAdapter.MovieSelectionViewHolder>(
        MovieSelectionDiffCallback()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSelectionViewHolder {
        return MovieSelectionViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieSelectionViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class MovieSelectionViewHolder private constructor(private val binding: ItemMovieSelectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movieInfo: MovieInfo, clickListener: MovieClickListener) {
            itemView.setOnClickListener {
                clickListener.onMovieClick(movieInfo)
            }
            with(binding) {
                tvMovieSelectionName.text = movieInfo.title
                tvMovieSelectionGenre.text = movieInfo.genres.joinToString(", ")
                ivMovieSelectionPoster.load(movieInfo.posterUrl)
                btnMovieSelection.setOnClickListener {
                    clickListener.onMovieClick(movieInfo)
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): MovieSelectionViewHolder {
                return MovieSelectionViewHolder(
                    ItemMovieSelectionBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}

class MovieSelectionDiffCallback : DiffUtil.ItemCallback<MovieInfo>() {
    override fun areItemsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieInfo, newItem: MovieInfo): Boolean {
        return oldItem == newItem
    }
}