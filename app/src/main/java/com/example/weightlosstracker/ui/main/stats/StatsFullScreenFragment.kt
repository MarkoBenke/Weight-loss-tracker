package com.example.weightlosstracker.ui.main.stats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentStatsFullScreenBinding
import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.util.BaseFragment
import com.example.weightlosstracker.util.viewBinding
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFullScreenFragment : BaseFragment<StatsViewModel, StatsWeightEntryViewData>(
    R.layout.fragment_stats_full_screen, StatsViewModel::class.java
), OnChartValueSelectedListener {

    private val binding by viewBinding(FragmentStatsFullScreenBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChartToggle()
    }

    override fun updateUi(model: StatsWeightEntryViewData) {
        if (model.success) {
            initWeightsChart(model)
            initChartLimitLines(model.stats)
        } else {
            //TODO handle error
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e?.data != null) {
            Toast.makeText(context, e.data.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected() {}

    private fun initChartToggle() {
        binding.toggle.check(R.id.weightButton)
        binding.toggle.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.weightButton -> {
                        val data = viewModel.modelLiveData.value
                        binding.weightChart.data = data?.weightsYData
                        initChartLimitLines(data?.stats)
                        binding.weightChart.invalidate()
                        binding.weightChart.animateX(StatsFragment.ANIMATE_DURATION)
                    }
                    R.id.waistButton -> {
                        binding.weightChart.data = viewModel.modelLiveData.value?.waistSizeYData
                        binding.weightChart.axisLeft.removeAllLimitLines()
                        binding.weightChart.invalidate()
                        binding.weightChart.animateX(StatsFragment.ANIMATE_DURATION)
                    }
                }
            }
        }
    }

    private fun initWeightsChart(it: StatsWeightEntryViewData) {
        val description = Description()
        description.text = ""
        with(binding) {
            weightChart.animateX(StatsFragment.ANIMATE_DURATION)
            weightChart.data = it.weightsYData
            weightChart.xAxis?.position = XAxis.XAxisPosition.BOTTOM
            weightChart.xAxis?.valueFormatter = IndexAxisValueFormatter(it.xData)
            weightChart.xAxis?.isGranularityEnabled = true
            weightChart.setOnChartValueSelectedListener(this@StatsFullScreenFragment)
            weightChart.setPinchZoom(true)
            weightChart.description = description
        }
    }

    private fun initChartLimitLines(stats: Stats?) {
        stats?.let {
            val startWeightLine = viewModel.createLimitLine(
                getString(R.string.start_weight_chart),
                it.startWeight
            )
            startWeightLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            val targetWeightLine = viewModel.createLimitLine(
                getString(R.string.target_weight_chart),
                it.targetWeight
            )
            targetWeightLine.labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
            val yAxis = binding.weightChart.axisLeft

            yAxis?.setDrawLimitLinesBehindData(true)
            yAxis?.addLimitLine(startWeightLine)
            yAxis?.addLimitLine(targetWeightLine)
        }
    }
}