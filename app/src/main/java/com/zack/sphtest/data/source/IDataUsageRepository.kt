package com.zack.sphtest.data.source

import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.Result

interface IDataUsageRepository {

    suspend fun getDataUsageList(forceUpdate: Boolean): Result<List<QuarterRecord>>
}