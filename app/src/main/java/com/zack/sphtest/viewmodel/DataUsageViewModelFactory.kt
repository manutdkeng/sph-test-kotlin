package com.zack.sphtest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zack.sphtest.data.source.IDataUsageRepository

class DataUsageViewModelFactory(
    private val repository: IDataUsageRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataUsageViewModel::class.java)) {
            return DataUsageViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}