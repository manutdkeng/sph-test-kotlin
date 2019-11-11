package com.zack.sphtest.data.source.remote

import com.zack.sphtest.data.QuarterRecord

data class DataUsageResponse(
    val records: List<QuarterRecord>
)