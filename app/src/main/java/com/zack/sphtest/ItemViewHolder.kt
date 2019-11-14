package com.zack.sphtest

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.zack.sphtest.data.FIRST_QUARTER
import com.zack.sphtest.data.YearlyRecord
import com.zack.sphtest.data.findMinVolumeQuarter
import com.zack.sphtest.databinding.ViewUsageItemBinding

class ItemViewHolder(private val binding: ViewUsageItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindData(record: YearlyRecord) {
        binding.yearlyRecord = record

        val quarter = record.records.findMinVolumeQuarter()

        // if the min volume is not in the first quarter mean there is a drop of volume in that year
        binding.clickImg.visibility = if (quarter == FIRST_QUARTER) View.GONE else View.VISIBLE
        binding.clickImg.setOnClickListener {
            Toast.makeText(
                itemView.context,
                "There is a drop of data usage volume in ${record.year} - $quarter",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}