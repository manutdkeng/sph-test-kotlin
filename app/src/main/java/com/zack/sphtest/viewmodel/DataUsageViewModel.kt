package com.zack.sphtest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.Result
import com.zack.sphtest.data.YearlyRecord
import com.zack.sphtest.data.source.IDataUsageRepository
import kotlinx.coroutines.launch

class DataUsageViewModel(
    private val repository: IDataUsageRepository
) : ViewModel() {

    private val _data = MutableLiveData<List<YearlyRecord>>()
    val data: LiveData<List<YearlyRecord>> = _data

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getDataUsageList() {
        _loadingState.value = true
        viewModelScope.launch {
            val result = repository.getDataUsageList(false)

            _loadingState.value = false
            if (result is Result.Success) {
                _data.value = processData(result.data)
            } else {
                _error.value = "Please try again later."
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            val result = repository.getDataUsageList(true)

            _loadingState.value = false
            if (result is Result.Success) {
                _data.value = processData(result.data)
            } else {
                _error.value = "Please try again later."
            }
        }
    }

    private fun processData(quarterRecords: List<QuarterRecord>): List<YearlyRecord> {
        val recordMap = HashMap<String, YearlyRecord>()

        quarterRecords.forEach {
            val year = it.quarter.subSequence(0, 4).toString()
            val volume = it.dataVolume.toDouble()
            val yearlyRecord: YearlyRecord

            if (recordMap.containsKey(year)) {
                yearlyRecord = recordMap[year]!!
                yearlyRecord.dataUsageVolume += volume
                yearlyRecord.records.add(it)
            } else {
                yearlyRecord = YearlyRecord(year, volume, mutableListOf(it))
                recordMap[year] = yearlyRecord
            }
        }

        val yearlyRecordList = recordMap.values.toList()

        return yearlyRecordList.sortedByDescending { it.year }
    }
}