package com.zack.sphtest.data

import com.squareup.moshi.Json
import com.zack.sphtest.data.source.local.DataUsageEntity

data class QuarterRecord(
    @Json(name = "volume_of_mobile_data") val dataVolume: String,
    val quarter: String,
    @Json(name = "_id") val id: Int
)

fun List<QuarterRecord>.asDatabaseEntity(): Array<DataUsageEntity> {
    return map {
        DataUsageEntity(
            id = it.id,
            quarter = it.quarter,
            volume = it.dataVolume
        )
    }.toTypedArray()
}