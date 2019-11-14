package com.zack.sphtest.viewmodel

import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.Result
import com.zack.sphtest.data.source.IDataUsageRepository

class FakeRepository : IDataUsageRepository {
    private val records = mutableListOf<QuarterRecord>()

    private var shouldReturnError = false

    fun addRecords(fakeRecords: List<QuarterRecord>) {
        records.addAll(fakeRecords)
    }

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getDataUsageList(forceUpdate: Boolean): Result<List<QuarterRecord>> {
        return if (shouldReturnError) {
            Result.Error(Exception("Test error"), "Test error case")
        } else {
            Result.Success(records)
        }
    }
}