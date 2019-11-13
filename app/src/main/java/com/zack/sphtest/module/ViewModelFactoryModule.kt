package com.zack.sphtest.module

import com.zack.sphtest.data.source.IDataUsageRepository
import com.zack.sphtest.viewmodel.DataUsageViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideDataUsageViewModelFactory(repository: IDataUsageRepository): DataUsageViewModelFactory {
        return DataUsageViewModelFactory(repository)
    }
}