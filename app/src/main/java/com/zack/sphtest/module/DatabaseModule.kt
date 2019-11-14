package com.zack.sphtest.module

import android.content.Context
import androidx.room.Room
import com.zack.sphtest.data.source.local.DataUsageDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideDatabase(): DataUsageDatabase {
        return Room.databaseBuilder(context.applicationContext,
            DataUsageDatabase::class.java,
            "sph_test_z_db").build()
    }
}