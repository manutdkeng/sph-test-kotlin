package com.zack.sphtest.module

import com.zack.sphtest.viewmodel.DataUsageViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class,
        RestClientModule::class,
        ViewModelFactoryModule::class]
)
interface ApplicationComponent {

    fun getDataUsageViewModelFactory(): DataUsageViewModelFactory
}