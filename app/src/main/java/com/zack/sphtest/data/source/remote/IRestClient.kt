package com.zack.sphtest.data.source.remote

import kotlinx.coroutines.Deferred

interface IRestClient {

    fun getDataUsageList(): Deferred<DataUsageResponse>
}