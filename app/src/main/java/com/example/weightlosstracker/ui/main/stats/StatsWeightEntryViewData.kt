package com.example.weightlosstracker.ui.main.stats

import com.example.weightlosstracker.domain.Stats
import com.github.mikephil.charting.data.LineData

data class StatsWeightEntryViewData(
    val yData: LineData?,
    val xData: ArrayList<String>?,
    val stats: Stats?,
    val success: Boolean
)