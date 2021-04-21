package com.marko.weightlosstracker.ui.main.stats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentStatsFullScreenBinding
import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
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
            Toast.makeText(requireContext(), getString(R.string.unknown_error), Toast.LENGTH_SHORT)
                .show()
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
                        binding.chart.data = data?.weightsYData
                        initChartLimitLines(data?.stats)
                        binding.chart.invalidate()
                        binding.chart.animateX(StatsFragment.ANIMATE_DURATION)
                    }
                    R.id.waistButton -> {
                        binding.chart.data = viewModel.modelLiveData.value?.waistSizeYData
                        binding.chart.axisLeft.removeAllLimitLines()
                        binding.chart.invalidate()
                        binding.chart.animateX(StatsFragment.ANIMATE_DURATION)
                    }
                }
            }
        }
    }

    private fun initWeightsChart(it: StatsWeightEntryViewData) {
        val description = Description()
        description.text = ""
        with(binding) {
            chart.animateX(StatsFragment.ANIMATE_DURATION)
            chart.data = it.weightsYData
            chart.xAxis?.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis?.valueFormatter = IndexAxisValueFormatter(it.xData)
            chart.xAxis?.isGranularityEnabled = true
            chart.setOnChartValueSelectedListener(this@StatsFullScreenFragment)
            chart.setPinchZoom(true)
            chart.description = description
        }
        setLabelsTextColor()
    }

    private fun initChartLimitLines(stats: Stats?) {
        stats?.let {
            val startWeightLine = viewModel.createLimitLine(
                getString(R.string.start_weight_chart),
                it.startWeight
            )
            startWeightLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
            startWeightLine.textColor =
                ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
            val targetWeightLine = viewModel.createLimitLine(
                getString(R.string.target_weight_chart),
                it.targetWeight
            )
            targetWeightLine.labelPosition = LimitLine.LimitLabelPosition.LEFT_BOTTOM
            targetWeightLine.textColor =
                ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
            val yAxis = binding.chart.axisLeft

            yAxis?.setDrawLimitLinesBehindData(true)
            yAxis?.addLimitLine(startWeightLine)
            yAxis?.addLimitLine(targetWeightLine)
        }
    }

    private fun setLabelsTextColor() {
        binding.chart.axisLeft.textColor =
            ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
        binding.chart.axisRight.textColor =
            ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
        binding.chart.description.textColor =
            ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
        binding.chart.legend.textColor =
            ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
        binding.chart.xAxis.textColor =
            ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
        binding.chart.data.setValueTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primaryTextColor
            )
        )
    }
}