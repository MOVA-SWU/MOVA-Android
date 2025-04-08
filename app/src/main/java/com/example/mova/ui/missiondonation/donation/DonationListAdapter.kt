package com.example.mova.ui.missiondonation.donation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.data.model.Company
import com.example.mova.databinding.ItemDonationListBinding

class CompanyListAdapter(private val clickListener: CompanyClickListener) : ListAdapter<Company, CompanyListAdapter.CompanyListViewHolder> (
    CompanyListDiffCallback()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyListViewHolder {
        return CompanyListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CompanyListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class CompanyListViewHolder private constructor(private val binding: ItemDonationListBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(company: Company, clickListener: CompanyClickListener) {
                binding.tvDonationCompanyName.text = company.companyName
                itemView.setOnClickListener {
                    clickListener.onCompanyClick(company.id.toString())
                }
            }

            companion object {
                fun from(parent: ViewGroup): CompanyListViewHolder {
                    return CompanyListViewHolder(
                        ItemDonationListBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
            }
        }
}

class CompanyListDiffCallback: DiffUtil.ItemCallback<Company>() {
    override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
        return oldItem == newItem
    }
}