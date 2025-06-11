package com.example.mova.ui.missiondonation.donation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.databinding.ItemCompanyMovieListBinding
import com.example.mova.ui.extensions.load

class CompanyMovieAdapter : ListAdapter<String, CompanyMovieAdapter.CompanyMovieViewHolder>(
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

        fun bind(imageUrl: String) {
            binding.ivCompanyDetailMovie.load(imageUrl)
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

class CompanyMovieDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}