package com.example.retrofitep.presentation.companies.delegate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitep.databinding.ItemCompanyBinding
import com.example.retrofitep.domain.companies.Company
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class CompanyDelegate(
    private val context: Context,
    private val listener: OnClickListener
) : AbsListItemAdapterDelegate<Company, Any, CompanyDelegate.CompanyViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    interface OnClickListener {
        fun onItemClick(company: Company, position: Int)
    }

    override fun isForViewType(company: Any, items: MutableList<Any>, position: Int): Boolean {
        return company is Company
    }

    override fun onCreateViewHolder(parent: ViewGroup): CompanyViewHolder {
        val binding = ItemCompanyBinding.inflate(layoutInflater, parent, false)
        return CompanyViewHolder(binding)
    }

    override fun onBindViewHolder(
        company: Company,
        holder: CompanyViewHolder,
        payloads: MutableList<Any>
    ) {
        with(holder) {
            tvName.text = company.name

            Glide.with(context)
                .load(company.img)
                .error(android.R.drawable.stat_notify_error)
                .into(ivImage)

            itemView.setOnClickListener { listener.onItemClick(company, adapterPosition) }
        }
    }

    class CompanyViewHolder(
        binding: ItemCompanyBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val tvName = binding.tvName
        val ivImage = binding.ivImage
    }

}