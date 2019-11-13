package com.zack.sphtest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zack.sphtest.data.YearlyRecord
import com.zack.sphtest.databinding.ViewUsageItemBinding

class DataUsageRecyclerViewAdapter(data: List<YearlyRecord>) :
    RecyclerView.Adapter<ItemViewHolder>() {
    private val dataRecords = mutableListOf<YearlyRecord>()

    init {
        dataRecords.addAll(data)
    }

    fun updateData(data: List<YearlyRecord>) {
        dataRecords.clear()
        dataRecords.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ViewUsageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataRecords.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val record = dataRecords[position]

        holder.bindData(record)
    }

}