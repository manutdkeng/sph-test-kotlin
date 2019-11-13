package com.zack.sphtest.module

import com.zack.sphtest.data.source.DataUsageRepository
import com.zack.sphtest.data.source.IDataUsageRepository
import com.zack.sphtest.data.source.remote.IRestClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(restClient: IRestClient): IDataUsageRepository {
        return DataUsageRepository(restClient)
    }
}