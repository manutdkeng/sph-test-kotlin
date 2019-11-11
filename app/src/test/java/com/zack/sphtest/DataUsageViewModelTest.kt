package com.zack.sphtest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.zack.sphtest.LiveDataTestUtil.getValue
import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.YearlyRecord
import com.zack.sphtest.viewmodel.DataUsageViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the implementation of [DataUsageViewModel]
 */
@ExperimentalCoroutinesApi
class DataUsageViewModelTest {

    private lateinit var dataUsageViewModel: DataUsageViewModel

    private lateinit var repository: FakeRepository

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val year19Record = YearlyRecord("2019", 21.01, mutableListOf())
    private val year18Record = YearlyRecord("2018", 11.11, mutableListOf())

    @Before
    fun setupViewModel() {
        repository = FakeRepository()
        val quarterRecord1 = QuarterRecord("21.01", "2019-Q1", 1)
        val quarterRecord2 = QuarterRecord("10.01", "2018-Q1", 2)
        val quarterRecord3 = QuarterRecord("1.0", "2018-Q2", 3)
        val quarterRecord4 = QuarterRecord("0.100", "2018-Q3", 4)

        year19Record.records.add(quarterRecord1)
        year18Record.records.add(quarterRecord2)
        year18Record.records.add(quarterRecord3)
        year18Record.records.add(quarterRecord4)

        val records = listOf(quarterRecord1, quarterRecord2, quarterRecord3, quarterRecord4)
        repository.addRecords(records)

        dataUsageViewModel = DataUsageViewModel(repository)
    }

    @Test
    fun getDataUsageFromRepository_success() {
        dataUsageViewModel.getDataUsageList()

        assertThat(getValue(dataUsageViewModel.data)[0].year).isEqualTo(year19Record.year)
        assertThat(getValue(dataUsageViewModel.data)[0].dataUsageVolume).isEqualTo(year19Record.dataUsageVolume)
        assertThat(getValue(dataUsageViewModel.data)[0].records.size).isEqualTo(1)

        assertThat(getValue(dataUsageViewModel.data)[1].year).isEqualTo(year18Record.year)
        assertThat(getValue(dataUsageViewModel.data)[1].dataUsageVolume).isEqualTo(year18Record.dataUsageVolume)
        assertThat(getValue(dataUsageViewModel.data)[1].records.size).isEqualTo(3)

    }

    @Test
    fun getDataUsageFromRepository_error() {
        repository.setReturnError(true)
        dataUsageViewModel.getDataUsageList()

        assertThat(getValue(dataUsageViewModel.error)).isEqualTo("Please try again later.")
    }

    @Test
    fun loadTask_loading() {
        mainCoroutineRule.pauseDispatcher()

        dataUsageViewModel.getDataUsageList()

        assertThat(getValue(dataUsageViewModel.loadingState)).isTrue()

        mainCoroutineRule.resumeDispatcher()
        assertThat(getValue(dataUsageViewModel.loadingState)).isFalse()
    }
}