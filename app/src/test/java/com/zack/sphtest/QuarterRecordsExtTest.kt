package com.zack.sphtest

import com.google.common.truth.Truth.assertThat
import com.zack.sphtest.data.QuarterRecord
import com.zack.sphtest.data.findMinVolumeQuarter
import org.junit.Test

class QuarterRecordsExtTest {

    @Test
    fun findMinVolume() {
        val quarterRecord1 = QuarterRecord("21.01", "2018-Q1", 1)
        val quarterRecord2 = QuarterRecord("10.01", "2018-Q2", 2)
        val quarterRecord3 = QuarterRecord("1.0", "2018-Q3", 3)
        val quarterRecord4 = QuarterRecord("0.100", "2018-Q4", 4)

        val records = listOf(quarterRecord1, quarterRecord2, quarterRecord3, quarterRecord4)

        assertThat(records.findMinVolumeQuarter()).isEqualTo("Q4")
    }

    @Test
    fun finMinVolume_sameVolume() {
        val quarterRecord1 = QuarterRecord("0.100", "2018-Q1", 1)
        val quarterRecord2 = QuarterRecord("10.01", "2018-Q2", 2)
        val quarterRecord3 = QuarterRecord("1.0", "2018-Q3", 3)
        val quarterRecord4 = QuarterRecord("0.100", "2018-Q4", 4)

        val records = listOf(quarterRecord1, quarterRecord2, quarterRecord3, quarterRecord4)

        assertThat(records.findMinVolumeQuarter()).isEqualTo("Q1")
    }

    @Test
    fun findMinVolume_emptyData() {
        val records = listOf<QuarterRecord>()

        assertThat(records.findMinVolumeQuarter()).isEqualTo("Q1")
    }
}