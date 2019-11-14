package com.zack.sphtest.data

const val FIRST_QUARTER = "Q1"
/**
 * Find the quarter which has the minimum data usage volume
 */
fun List<QuarterRecord>.findMinVolumeQuarter(): String {
    val minVolumeRecord = this.minBy { it.dataVolume }
    val quarter = minVolumeRecord?.quarter?.subSequence(
        minVolumeRecord.quarter.length - 2,
        minVolumeRecord.quarter.length
    )
        ?: "Q1"

    return quarter.toString()
}