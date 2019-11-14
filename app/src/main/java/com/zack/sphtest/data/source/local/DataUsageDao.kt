package com.zack.sphtest.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataUsageDao {

    @Query("SELECT * FROM data_usage_table")
    fun getAllDataUsage(): List<DataUsageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg entity: DataUsageEntity)
}