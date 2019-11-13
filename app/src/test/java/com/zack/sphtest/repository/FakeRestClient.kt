package com.zack.sphtest.repository

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zack.sphtest.data.source.remote.DataUsageResponse
import com.zack.sphtest.data.source.remote.IDataUsageApiService
import com.zack.sphtest.data.source.remote.IRestClient
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * implement a fake RestClient for [DataUsageRepositoryTest]
 */
class FakeRestClient(fakeInterceptor: FakeInterceptor) : IRestClient {
    private val retrofitService: IDataUsageApiService

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(fakeInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("http://fakeurl.zack.com")
            .client(okHttpClient)
            .build()

        retrofitService = retrofit.create(IDataUsageApiService::class.java)
    }

    override fun getDataUsageList(): Deferred<DataUsageResponse> {
        return retrofitService.getDataUsageList("123456")
    }
}