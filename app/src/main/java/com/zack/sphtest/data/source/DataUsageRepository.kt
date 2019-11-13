package com.zack.sphtest.data.source

import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.Result
import com.zack.sphtest.data.Result.Error
import com.zack.sphtest.data.Result.Success
import com.zack.sphtest.data.source.remote.IRestClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataUsageRepository(
    private val restClient: IRestClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IDataUsageRepository {

    override suspend fun getDataUsageList(): Result<List<QuarterRecord>> {
        return withContext(ioDispatcher) {

            val getDataUsageDeferred = restClient.getDataUsageList()
            try {
                val result = getDataUsageDeferred.await()

                return@withContext Success(result.result.records.sortedByDescending { it.quarter })

            } catch (e: Exception) {
                return@withContext Error(e, "Api Failed")
            }
        }
    }
}