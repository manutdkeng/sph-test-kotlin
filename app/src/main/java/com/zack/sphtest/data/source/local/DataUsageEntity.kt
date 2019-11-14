package com.zack.sphtest.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zack.sphtest.data.QuarterRecord

@Entity(tableName = "data_usage_table")
data class DataUsageEntity(
    @PrimaryKey
    val id: Int,
    val quarter: String,
    val volume: String
)

fun List<DataUsageEntity>.asDomainModel(): List<QuarterRecord> {
    return map {
        QuarterRecord(
            dataVolume = it.volume,
            quarter = it.quarter,
            id = it.id
        )
    }
}