package com.example.mova.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.R
import com.example.mova.data.model.Movie
import com.example.mova.databinding.ItemHomeMovieListBinding

class HomeMovieAdapter(private val clickListener: MovieClickListener) : ListAdapter<Movie, HomeMovieAdapter.HomeMovieViewHolder>(
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

        fun bind(movie: Movie, clickListener: MovieClickListener) {
            binding.ivHomeMovieList.setBackgroundResource(R.color.gray_55)
            binding.ivHomeMovieList.setOnClickListener {
                clickListener.onMovieClick(movie.id.toString())
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

class MovieDiffCallback: DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}