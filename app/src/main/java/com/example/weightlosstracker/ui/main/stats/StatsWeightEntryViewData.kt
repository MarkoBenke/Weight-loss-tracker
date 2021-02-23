package com.example.weightlosstracker.ui.main.stats

import com.example.weightlosstracker.domain.Stats
import com.github.mikephil.charting.data.LineData

data class StatsWeightEntryViewData(
    val weightsYData: LineData?,
    val waistSizeYData: LineData?,
    val xData: ArrayList<String>?,
    val stats: Stats?,
    val success: Boolean
)