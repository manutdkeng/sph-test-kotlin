package com.zack.sphtest.repository

import com.google.common.truth.Truth.assertThat
import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.Result.Error
import com.zack.sphtest.data.Result.Success
import com.zack.sphtest.data.source.DataUsageRepository
import com.zack.sphtest.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for the implementation of [DataUsageRepository]
 */
class DataUsageRepositoryTest {

    private lateinit var fakeInterceptor: FakeInterceptor
    private lateinit var dataUsageRepository: DataUsageRepository

    @Before
    fun createRepository() {
        fakeInterceptor = FakeInterceptor()
        val quarterRecord1 = QuarterRecord("21.01", "2019-Q1", 1)
        val quarterRecord2 = QuarterRecord("10.01", "2018-Q1", 2)
        val quarterRecord3 = QuarterRecord("1.0", "2018-Q2", 3)
        val quarterRecord4 = QuarterRecord("0.100", "2018-Q3", 4)
        fakeInterceptor.addRecords(
            listOf(
                quarterRecord1,
                quarterRecord2,
                quarterRecord3,
                quarterRecord4
            )
        )

        val restClient = FakeRestClient(fakeInterceptor)
        dataUsageRepository = DataUsageRepository(restClient, Dispatchers.Unconfined)
    }

    @Test
    fun testGetDataUsage_success() = runBlocking {
        val result = dataUsageRepository.getDataUsageList()

        assertThat(result is Success).isTrue()
        assertThat(result.succeeded).isTrue()

        val records = result as Success

        assertThat(records.data[0].quarter).isEqualTo("2019-Q1")
        assertThat(records.data[1].quarter).isEqualTo("2018-Q3")
    }

    @Test
    fun testGetDataUsage_error() = runBlocking {
        fakeInterceptor.returnError(true)

        val result = dataUsageRepository.getDataUsageList()

        assertThat(result is Error).isTrue()
        assertThat(result.succeeded).isFalse()
    }
}