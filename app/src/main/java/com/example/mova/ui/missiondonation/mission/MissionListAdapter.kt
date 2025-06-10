package com.example.mova.ui.missiondonation.mission

import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.data.model.response.MissionListResponse
import com.example.mova.databinding.ItemMissionListBinding

private const val VIEW_TYPE_AVAILABLE = 0
private const val VIEW_TYPE_COMPLETED = 1

class MissionListAdapter(private val clickListener: MissionClickListener) : ListAdapter<MissionListResponse, RecyclerView.ViewHolder> (
    MissionListDiffCallback()
){
    var isAvailableMode: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                val currentListCopy = currentList.toList()
                submitList(null) {
                    submitList(currentListCopy)
                }
            }
        }

    override fun getItemViewType(position: Int): Int {
        return if (isAvailableMode) VIEW_TYPE_AVAILABLE else VIEW_TYPE_COMPLETED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            VIEW_TYPE_AVAILABLE -> {
                MissionListViewHolder.from(parent)
            }
            VIEW_TYPE_COMPLETED -> {
                MissionCompleteViewHolder.from(parent)
            }
            else -> throw IllegalArgumentException("Invalid viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mission = getItem(position)
        when (holder) {
            is MissionListViewHolder -> holder.bind(mission, clickListener)
            is MissionCompleteViewHolder -> holder.bind(mission, clickListener)
        }
    }

    class MissionListViewHolder private constructor(private val binding: ItemMissionListBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(mission: MissionListResponse, clickListener: MissionClickListener) {
                binding.tvMissionListTitle.text = mission.mission
                binding.tvMissionListPoint.text = "${mission.cost}포인트 받기"
                itemView.setOnClickListener {
                    clickListener.onMissionClick(mission)
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

class MissionCompleteViewHolder private constructor(private val binding: ItemMissionListBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(mission: MissionListResponse, clickListener: MissionClickListener) {
        binding.tvMissionListTitle.text = mission.mission
        binding.tvMissionListPoint.visibility = View.GONE
        val layoutParams = binding.tvMissionListTitle.layoutParams as ViewGroup.MarginLayoutParams
        val marginInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            30f,
            binding.tvMissionListTitle.context.resources.displayMetrics
        ).toInt()

        layoutParams.topMargin = marginInPx
        layoutParams.bottomMargin = marginInPx
        binding.tvMissionListTitle.layoutParams = layoutParams

        itemView.setOnClickListener {
            clickListener.onMissionClick(mission)
        }
    }

    companion object {
        fun from(parent: ViewGroup): MissionCompleteViewHolder {
            return MissionCompleteViewHolder(
                ItemMissionListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }
}

class MissionListDiffCallback: DiffUtil.ItemCallback<MissionListResponse>() {
    override fun areItemsTheSame(oldItem: MissionListResponse, newItem: MissionListResponse): Boolean {
        return oldItem.myMissionId == newItem.myMissionId
    }

    override fun areContentsTheSame(oldItem: MissionListResponse, newItem: MissionListResponse): Boolean {
        return oldItem == newItem
    }
}