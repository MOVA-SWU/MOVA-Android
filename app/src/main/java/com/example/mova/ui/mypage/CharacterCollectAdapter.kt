package com.example.mova.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.R
import com.example.mova.data.model.Character
import com.example.mova.databinding.ItemCharacterCollectBinding

class CharacterCollectAdapter : ListAdapter<Character, CharacterCollectAdapter.CharacterCollectViewHolder>(
    CharacterCollectDiffCallback()
) {
    private var actualItem: List<Character> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterCollectViewHolder {
        return CharacterCollectViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterCollectViewHolder, position: Int) {
        if (position < actualItem.size) {
            holder.bind(getItem(position), isPlaceholder = false)
        } else {
            holder.bind(null, isPlaceholder = true)
        }
    }

    override fun getItemCount(): Int {
        return maxOf(9, super.getItemCount())
    }

    fun submitWithPlaceholders(item: List<Character>) {
        actualItem = item
        submitList(item)
    }

    class CharacterCollectViewHolder private constructor(private val binding: ItemCharacterCollectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character?, isPlaceholder: Boolean) {
            if (isPlaceholder) {
                binding.ivCharacterCollect.setBackgroundResource(R.color.white_100)
            } else if (character != null) {
                binding.ivCharacterCollect.setBackgroundResource(R.color.gray_55)
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

class CharacterCollectDiffCallback: DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}