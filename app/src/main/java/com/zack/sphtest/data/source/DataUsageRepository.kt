package com.zack.sphtest.data.source

import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.Result
import com.zack.sphtest.data.Result.Error
import com.zack.sphtest.data.Result.Success
import com.zack.sphtest.data.asDatabaseEntity
import com.zack.sphtest.data.source.local.DataUsageDatabase
import com.zack.sphtest.data.source.local.asDomainModel
import com.zack.sphtest.data.source.remote.IRestClient
import kotlinx.coroutines.*

class DataUsageRepository(
    private val restClient: IRestClient,
    private val database: DataUsageDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IDataUsageRepository {

    override suspend fun getDataUsageList(forceUpdate: Boolean): Result<List<QuarterRecord>> {
        return withContext(ioDispatcher) {
            if (forceUpdate) {
                return@withContext getRemoteData()
            } else {
                val localData = database.dataUsageDao.getAllDataUsage()
                return@withContext if (localData.isEmpty()) getRemoteData() else Success(localData.asDomainModel())
            }
        }
    }

    private suspend fun getRemoteData(): Result<List<QuarterRecord>> {
        return withContext(ioDispatcher) {
            val getDataUsageDeferred = restClient.getDataUsageList()
            try {
                val result = getDataUsageDeferred.await()
                launch {
                    saveData(result.result.records)
                }
                return@withContext Success(result.result.records.sortedByDescending { it.quarter })

            } catch (e: Exception) {
                return@withContext Error(e, "Api Failed")
            }
        }
    }

    private suspend fun saveData(records: List<QuarterRecord>) {
        coroutineScope {
            database.dataUsageDao.insertAll(*records.asDatabaseEntity())
        }
    }
}