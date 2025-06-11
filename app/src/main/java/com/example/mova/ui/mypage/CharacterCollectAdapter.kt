package com.example.mova.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.R
import com.example.mova.databinding.ItemCharacterCollectBinding
import com.example.mova.ui.extensions.load

class CharacterCollectAdapter : ListAdapter<String, CharacterCollectAdapter.CharacterCollectViewHolder>(
    CharacterCollectDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCollectViewHolder {
        return CharacterCollectViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterCollectViewHolder, position: Int) {
        val item = getItem(position)
        val isPlaceholder = item == null
        holder.bind(item, isPlaceholder)
    }

    fun submitWithPlaceholders(item: List<String>) {
        val fixedList = MutableList<String?>(9) { null }
        for (i in item.indices) {
            fixedList[i] = item[i]
        }
        submitList(fixedList)
    }

    class CharacterCollectViewHolder private constructor(private val binding: ItemCharacterCollectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: String?, isPlaceholder: Boolean) {
            if (isPlaceholder) {
                binding.ivCharacterCollect.setBackgroundResource(R.color.gray_55)
                binding.ivCharacterCollect.setImageDrawable(null)
            } else if (character != null) {
                binding.ivCharacterCollect.load(character)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CharacterCollectViewHolder {
                return CharacterCollectViewHolder(
                    ItemCharacterCollectBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }
}

class CharacterCollectDiffCallback: DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}