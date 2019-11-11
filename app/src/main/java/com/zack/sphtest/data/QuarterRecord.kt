package com.zack.sphtest.data

import com.squareup.moshi.Json

data class QuarterRecord(
    @Json(name = "volume_of_mobile_data") val dataVolume: String,
    val quarter: String,
    @Json(name = "_id") val id: Int
)