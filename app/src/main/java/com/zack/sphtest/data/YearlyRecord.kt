package com.zack.sphtest.data

data class YearlyRecord(
    val year: String,
    var dataUsageVolume: Double,
    val records: MutableList<QuarterRecord>
)