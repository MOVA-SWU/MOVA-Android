package com.example.mova.ui.missiondonation.donation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.R
import com.example.mova.data.model.Movie
import com.example.mova.databinding.ItemCompanyMovieListBinding

class CompanyMovieAdapter : ListAdapter<Movie, CompanyMovieAdapter.CompanyMovieViewHolder>(
    CompanyMovieDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyMovieViewHolder {
        return CompanyMovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CompanyMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CompanyMovieViewHolder private constructor(private val binding: ItemCompanyMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.ivCompanyDetailMovie.setBackgroundResource(R.color.gray_55)
        }

        companion object {
            fun from(parent: ViewGroup): CompanyMovieViewHolder {
                return CompanyMovieViewHolder(
                    ItemCompanyMovieListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}

class CompanyMovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}