package com.example.mova.ui.missiondonation.donation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mova.data.model.response.CompanyListResponse
import com.example.mova.databinding.ItemDonationListBinding

class CompanyListAdapter(private val clickListener: CompanyClickListener) : ListAdapter<CompanyListResponse, CompanyListAdapter.CompanyListViewHolder> (
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

            fun bind(company: CompanyListResponse, clickListener: CompanyClickListener) {
                binding.tvDonationCompanyName.text = company.name
                itemView.setOnClickListener {
                    clickListener.onCompanyClick(company)
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

class CompanyListDiffCallback: DiffUtil.ItemCallback<CompanyListResponse>() {
    override fun areItemsTheSame(oldItem: CompanyListResponse, newItem: CompanyListResponse): Boolean {
        return oldItem.companyId == newItem.companyId
    }

    override fun areContentsTheSame(oldItem: CompanyListResponse, newItem: CompanyListResponse): Boolean {
        return oldItem == newItem
    }
}