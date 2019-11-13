package com.zack.sphtest

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zack.sphtest.data.YearlyRecord
import com.zack.sphtest.databinding.ViewUsageItemBinding

class ItemViewHolder(private val binding: ViewUsageItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(record: YearlyRecord) {
        binding.yearlyRecord = record

        val minVolumeRecord = record.records.minBy { it.dataVolume }
        val quarter = minVolumeRecord?.quarter?.subSequence(
            minVolumeRecord.quarter.length - 2,
            minVolumeRecord.quarter.length
        )
            ?: "Q1"

        binding.clickImg.visibility = if (quarter == "Q1") View.GONE else View.VISIBLE

        binding.clickImg.setOnClickListener {
            Toast.makeText(
                itemView.context,
                "There is a drop of data usage volume in ${minVolumeRecord?.quarter}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}