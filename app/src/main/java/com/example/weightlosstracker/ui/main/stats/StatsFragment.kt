package com.example.weightlosstracker.ui.main.stats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentStatsBinding
import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.util.*
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatsFragment : BaseFragment<StatsViewModel, StatsWeightEntryViewData>(
    R.layout.fragment_stats,
    StatsViewModel::class.java
), OnChartValueSelectedListener {

    private val binding by viewBinding(FragmentStatsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChartToggle()
        initFullScreenListener()
    }

    override fun updateUi(model: StatsWeightEntryViewData) {
        if (model.success) {
            initChart(model)
            initChartLimitLines(model.stats)
            initStatsUi(model.stats)
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

    private fun initFullScreenListener() {
        binding.fullscreen.setOnClickListener {
            startActivity(Intent(requireActivity(), StatsFullScreenActivity::class.java))
        }
    }

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
                        binding.chart.animateX(ANIMATE_DURATION)
                    }
                    R.id.waistButton -> {
                        binding.chart.data = viewModel.modelLiveData.value?.waistSizeYData
                        binding.chart.axisLeft.removeAllLimitLines()
                        binding.chart.invalidate()
                        binding.chart.animateX(ANIMATE_DURATION)
                    }
                }
            }
        }
    }

    private fun initStatsUi(stats: Stats?) {
        stats?.let {
            binding.currentWeight.text =
                getString(R.string.kg, it.currentWeight.roundUp().toString())
            binding.targetWeight.text =
                getString(R.string.kg, it.targetWeight.roundUp().toString())
            binding.totalLoss.text =
                getString(R.string.kg, it.totalLoss.roundUp().toString())
            binding.remaining.text = getString(R.string.kg, it.remaining.roundUp().toString())
            binding.bmiCategory.text = getBmiCategory(it.bmi)
            binding.caloriesBurned.text =
                getString(R.string.kCal, it.caloriesBurned.roundUp().toString())
            binding.cheeseburgersBurned.text = it.cheeseburgersBurned.roundUp().toString()
            if (it.waistSizeLoss != 0) {
                binding.totalWaistSizeLayout.isVisible = true
                binding.totalWaistSizeLoss.text =
                    getString(R.string.cm, it.waistSizeLoss.toString())
            } else {
                binding.totalWaistSizeLayout.isVisible = false
            }
            if (it.currentWaistSize != 0) {
                binding.waistSizeLayout.isVisible = true
                binding.currentWaistSize.text =
                    getString(R.string.cm, it.currentWaistSize.toString())
            } else {
                binding.waistSizeLayout.isVisible = false
            }
        }
        binding.totalEntries.text = viewModel.getTotalEntries()
        binding.worstRecord.text = getString(R.string.kg, viewModel.getWorstRecord())
        binding.bestRecord.text = getString(R.string.kg, viewModel.getBestRecord())
    }

    private fun initChart(it: StatsWeightEntryViewData) {
        val description = Description()
        description.text = ""
        with(binding) {
            chart.animateX(ANIMATE_DURATION)
            chart.data = it.weightsYData
            chart.xAxis?.position = XAxis.XAxisPosition.BOTTOM
            chart.xAxis?.valueFormatter = IndexAxisValueFormatter(it.xData)
            chart.xAxis?.isGranularityEnabled = true
            chart.setOnChartValueSelectedListener(this@StatsFragment)
            chart.setPinchZoom(true)
            chart.description = description
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
            val yAxis = binding.chart.axisLeft

            yAxis?.setDrawLimitLinesBehindData(true)
            yAxis?.addLimitLine(startWeightLine)
            yAxis?.addLimitLine(targetWeightLine)
        }
    }

    private fun getBmiCategory(bmi: Float): String {
        return if (bmi < Constants.UNDERWEIGHT) {
            getString(R.string.bmi_underweight)
        } else if (bmi > Constants.UNDERWEIGHT && bmi <= Constants.NORMAL_END) {
            getString(R.string.bmi_normal)
        } else if (bmi > Constants.NORMAL_END && bmi <= Constants.OVERWEIGHT) {
            getString(R.string.bmi_overweight)
        } else if (bmi > Constants.OVERWEIGHT && bmi <= Constants.OBESE_I) {
            getString(R.string.bmi_obese_first)
        } else if (bmi > Constants.OBESE_I && bmi <= Constants.OBESE_II) {
            getString(R.string.bmi_obese_second)
        } else if (bmi > Constants.OBESE_II) {
            getString(R.string.bmi_obese_third)
        } else ""
    }

    companion object {
        const val ANIMATE_DURATION = 800
    }
}