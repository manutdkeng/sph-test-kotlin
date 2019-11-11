package com.zack.sphtest.data.source.remote

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataUsageApiService {

    @GET("api/action/datastore_search")
    fun getDataUsageList(@Query("resource_id") resourceId: String): Deferred<DataUsageResponse>
}