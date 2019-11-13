package com.zack.sphtest.module

import com.zack.sphtest.data.source.remote.IRestClient
import com.zack.sphtest.data.source.remote.RestClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RestClientModule {

    @Provides
    @Singleton
    fun provideRestClient(): IRestClient {
        return RestClient()
    }
}