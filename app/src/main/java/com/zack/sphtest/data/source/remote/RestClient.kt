package com.zack.sphtest.data.source.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "https://data.gov.sg/"
private const val RESOURCE_ID = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"

class RestClient : IRestClient {
    private val retrofitService: IDataUsageApiService

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .build()

        retrofitService = retrofit.create(IDataUsageApiService::class.java)
    }

    override fun getDataUsageList(): Deferred<DataUsageResponse> {
        return retrofitService.getDataUsageList(RESOURCE_ID)
    }
}