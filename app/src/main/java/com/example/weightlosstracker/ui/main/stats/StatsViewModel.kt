package com.example.weightlosstracker.ui.main.stats

import android.graphics.Color
import androidx.lifecycle.viewModelScope
import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.util.BaseViewModel
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.DispatcherProvider
import com.example.weightlosstracker.util.parseNoYearDate
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val weightEntryRepository: WeightEntryRepository,
    private val dispatcherProvider: DispatcherProvider
) : BaseViewModel<StatsWeightEntryViewData>() {

    override fun fetchInitialData() {
        getUserStats()
    }

    fun getTotalEntries(): String {
        return modelLiveData.value?.weightsYData?.entryCount.toString()
    }

    fun getBestRecord(): String {
        return modelLiveData.value?.weightsYData?.yMin.toString()
    }

    fun getWorstRecord(): String {
        return modelLiveData.value?.weightsYData?.yMax.toString()
    }

    fun createLimitLine(label: String, limit: Float): LimitLine {
        val line = LimitLine(limit, label)
        line.lineWidth = 2f
        line.enableDashedLine(25f, 30f, 0f)
        line.textSize = 10f
        return line
    }

    private fun getUserStats() {
        viewModelScope.launch(dispatcherProvider.io) {
            weightEntryRepository.getUserStats().collect { stats ->
                getAllEntries(stats)
            }
        }
    }

    private fun getAllEntries(stats: Stats) {
        viewModelScope.launch(dispatcherProvider.io) {
            weightEntryRepository.getAllEntries().collect {
                when (it) {
                    is DataState.Success -> {
                        modelLiveData.postValue(
                            StatsWeightEntryViewData(
                                mapWeightsYData(it.data.reversed()),
                                mapWaistSizeYData(it.data.reversed()),
                                mapXData(it.data.reversed()),
                                stats,
                                true
                            )
                        )
                    }
                    is DataState.Error -> {
                        modelLiveData.postValue(
                            StatsWeightEntryViewData(null, null, null, null, false)
                        )
                    }
                    DataState.Loading -> {
                    }
                }
            }
        }
    }

    private fun mapWeightsYData(data: List<WeightEntry>): LineData {
        if (data.isNotEmpty()) {
            val yValues: ArrayList<Entry> = arrayListOf()
            data.forEachIndexed { index, element ->
                yValues.add(
                    Entry(
                        index.toFloat(),
                        element.currentWeight,
                        element.date
                    )
                )
            }

            val set1 = LineDataSet(yValues, "Weight entries")
            set1.color = Color.RED
            set1.lineWidth = 2f
            set1.valueTextSize = 12f

            val dataSets: ArrayList<ILineDataSet> = arrayListOf()
            dataSets.add(set1)
            return LineData(dataSets)
        }
        return LineData()
    }

    private fun mapWaistSizeYData(data: List<WeightEntry>): LineData {
        if (data.isNotEmpty()) {
            val yValues: ArrayList<Entry> = arrayListOf()
            data.forEachIndexed { index, element ->
                yValues.add(
                    Entry(
                        index.toFloat(),
                        element.waistSize.toFloat(),
                        element.date
                    )
                )
            }

            val set1 = LineDataSet(yValues, "Waist size entries")
            set1.color = Color.RED
            set1.lineWidth = 2f
            set1.valueTextSize = 12f

            val dataSets: ArrayList<ILineDataSet> = arrayListOf()
            dataSets.add(set1)
            return LineData(dataSets)
        }
        return LineData()
    }

    private fun mapXData(data: List<WeightEntry>?): ArrayList<String> {
        if (data!!.isNotEmpty()) {
            val dates: ArrayList<String> = arrayListOf()
            data.forEach {
                dates.add(parseNoYearDate(it.date))
            }
            return dates
        }
        return arrayListOf()
    }
}