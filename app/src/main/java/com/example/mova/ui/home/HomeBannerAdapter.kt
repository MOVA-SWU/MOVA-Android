package com.example.mova.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.R
import com.example.mova.data.model.Banner
import com.example.mova.databinding.ItemHomeBannerBinding

class HomeBannerAdapter(private val clickListener: MovieClickListener) : ListAdapter<Banner, HomeBannerAdapter.HomeMovieViewHolder>(
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

        fun bind(banner: Banner, clickListener: MovieClickListener) {
            binding.ivBannerImage.setBackgroundResource(R.color.gray_55)
            binding.ivBannerImage.setOnClickListener {
                clickListener.onMovieClick(banner.id.toString())
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

class BannerDiffCallback: DiffUtil.ItemCallback<Banner>() {

    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }
}