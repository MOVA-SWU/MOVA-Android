package com.example.mova.ui.missiondonation.mission

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.data.model.Mission
import com.example.mova.databinding.ItemMissionListBinding
import com.example.mova.ui.home.MovieClickListener

class MissionListAdapter(private val clickListener: MovieClickListener) : ListAdapter<Mission, MissionListAdapter.MissionListViewHolder> (
    MissionListDiffCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MissionListViewHolder {
        return MissionListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MissionListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class MissionListViewHolder private constructor(private val binding: ItemMissionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(mission: Mission, clickListener: MovieClickListener) {
                binding.tvMissionListTitle.text = mission.missionTitle
                binding.tvMissionListPoint.text = "${mission.point}포인트 받기"
                itemView.setOnClickListener {
                    clickListener.onMovieClick(mission.id.toString())
                }
            }

            companion object {
                fun from(parent: ViewGroup): MissionListViewHolder {
                    return MissionListViewHolder(
                        ItemMissionListBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
            }
        }
}

class MissionListDiffCallback: DiffUtil.ItemCallback<Mission>() {
    override fun areItemsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Mission, newItem: Mission): Boolean {
        return oldItem == newItem
    }
}