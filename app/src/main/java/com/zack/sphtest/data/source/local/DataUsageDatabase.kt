package com.zack.sphtest.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataUsageEntity::class], version = 1, exportSchema = false)
abstract class DataUsageDatabase : RoomDatabase() {

    abstract val dataUsageDao: DataUsageDao
}