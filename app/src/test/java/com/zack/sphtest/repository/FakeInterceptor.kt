package com.zack.sphtest.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.source.remote.DataUsageResponse
import com.zack.sphtest.data.source.remote.DataUsageResult
import okhttp3.*

/**
 * Create a FakeInterceptor to intercept the network call
 * and return desired response
 */
class FakeInterceptor : Interceptor {

    private var shouldReturnError: Boolean = false
    private val records = mutableListOf<QuarterRecord>()

    fun returnError(value: Boolean) {
        shouldReturnError = value
    }

    fun addRecords(fakeData: List<QuarterRecord>) {
        records.addAll(fakeData)
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val jsonAdapter = moshi.adapter(DataUsageResponse::class.java)
        val response = DataUsageResponse(DataUsageResult(records))
        val responseStr = jsonAdapter.toJson(response)

        return if (shouldReturnError) {
            Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(400)
                .message("Error")
                .body(ResponseBody.create(MediaType.parse("application/json"), "Error"))
                .build()

        } else {
            Response.Builder()
                .code(200)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .message(responseStr)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseStr))
                .addHeader("content-type", "application/json")
                .build()
        }
    }
}