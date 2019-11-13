package com.zack.sphtest

import android.app.Application
import com.zack.sphtest.module.ApplicationComponent
import com.zack.sphtest.module.DaggerApplicationComponent

class SphApplication : Application() {

    private lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        component = DaggerApplicationComponent.builder().build()
    }

    fun getAppComponent(): ApplicationComponent {
        return component
    }

    companion object {
        lateinit var instance: SphApplication
    }
}