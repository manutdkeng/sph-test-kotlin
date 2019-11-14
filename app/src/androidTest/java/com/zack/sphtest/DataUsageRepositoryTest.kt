package com.zack.sphtest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.Result.Error
import com.zack.sphtest.data.Result.Success
import com.zack.sphtest.data.asDatabaseEntity
import com.zack.sphtest.data.source.DataUsageRepository
import com.zack.sphtest.data.source.local.DataUsageDatabase
import com.zack.sphtest.data.succeeded
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class DataUsageRepositoryTest {

    private lateinit var fakeInterceptor: FakeInterceptor
    private lateinit var dataUsageRepository: DataUsageRepository
    private lateinit var fakeDatabase: DataUsageDatabase

    private val quarterRecord1 = QuarterRecord("21.01", "2019-Q1", 1)
    private val quarterRecord2 = QuarterRecord("10.01", "2018-Q1", 2)
    private val quarterRecord3 = QuarterRecord("1.0", "2018-Q2", 3)
    private val quarterRecord4 = QuarterRecord("0.100", "2018-Q3", 4)


    @Before
    fun createRepository() {
        fakeInterceptor = FakeInterceptor()
        fakeInterceptor.addRecords(
            listOf(
                quarterRecord1,
                quarterRecord2,
                quarterRecord3,
                quarterRecord4
            )
        )

        fakeDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataUsageDatabase::class.java
        ).build()

        val restClient = FakeRestClient(fakeInterceptor)
        dataUsageRepository = DataUsageRepository(restClient, fakeDatabase, Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        fakeDatabase.close()
    }

    @Test
    fun testGetDataUsage_localEmpty_success() = runBlocking {
        val result = dataUsageRepository.getDataUsageList(false)

        assertThat(result is Success).isTrue()
        assertThat(result.succeeded).isTrue()

        val records = result as Success
        assertThat(records.data.size).isEqualTo(4)
        assertThat(records.data[0].quarter).isEqualTo("2019-Q1")
        assertThat(records.data[1].quarter).isEqualTo("2018-Q3")
    }

    @Test
    fun testGetDataUsage_localEmpty_error() = runBlocking {
        fakeInterceptor.returnError(true)

        val result = dataUsageRepository.getDataUsageList(true)

        assertThat(result is Error).isTrue()
        assertThat(result.succeeded).isFalse()
    }

    @Test
    fun testGetDataUsage_localNotEmpty() = runBlocking {
        // insert 1 data to DB
        fakeDatabase.dataUsageDao.insertAll(*listOf(quarterRecord1).asDatabaseEntity())

        val records = dataUsageRepository.getDataUsageList(false) as Success

        assertThat(records.data.size).isEqualTo(1)
        assertThat(records.data[0].quarter).isEqualTo("2019-Q1")
    }

    @Test
    fun testGetDataUsage_forceUpdate() = runBlocking {
        fakeDatabase.dataUsageDao.insertAll(*listOf(quarterRecord1).asDatabaseEntity())

        val records = dataUsageRepository.getDataUsageList(true) as Success

        assertThat(records.data.size).isEqualTo(4)
        assertThat(records.data[0].quarter).isEqualTo("2019-Q1")
        assertThat(records.data[1].quarter).isEqualTo("2018-Q3")

        val localData = fakeDatabase.dataUsageDao.getAllDataUsage()
        assertThat(localData.size).isEqualTo(4)
    }

    @Test
    fun testGetDataUsage_forceUpdate_saveToLocal() = runBlocking {
        fakeDatabase.dataUsageDao.insertAll(*listOf(quarterRecord1).asDatabaseEntity())

        val records = dataUsageRepository.getDataUsageList(true) as Success

        val localData = fakeDatabase.dataUsageDao.getAllDataUsage()
        assertThat(localData.size).isEqualTo(records.data.size)
    }
}