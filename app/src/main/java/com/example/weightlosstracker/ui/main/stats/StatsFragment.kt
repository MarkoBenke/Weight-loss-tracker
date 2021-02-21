package com.example.weightlosstracker.ui.main.stats

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentStatsBinding
import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.util.Constants
import com.example.weightlosstracker.util.makeShort
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
class StatsFragment : Fragment(R.layout.fragment_stats), OnChartValueSelectedListener {

    private val binding by viewBinding(FragmentStatsBinding::bind)
    private val viewModel: StatsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeToObservers()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e?.data != null) {
            Toast.makeText(context, e.data.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onNothingSelected() {}

    private fun subscribeToObservers() {
        viewModel.userStatsLiveData.observe(viewLifecycleOwner, Observer {
            if (it.success) {
                initChart(it)
                initChartLimitLines(it.stats)
                initStatsUi(it.stats)
            } else {
                //TODO handle error
            }
        })
    }

    private fun initStatsUi(stats: Stats?) {
        stats?.let {
            binding.currentWeight.text = getString(R.string.kg, it.currentWeight.toString().makeShort())
            binding.targetWeight.text = getString(R.string.kg, it.targetWeight.toString().makeShort())
            binding.totalLoss.text = getString(R.string.kg, it.totalLoss.toString().makeShort())
            binding.remaining.text = getString(R.string.kg, it.remaining.toString().makeShort())
            binding.bmiCategory.text = getBmiCategory(it.bmi)
            binding.caloriesBurned.text = getString(R.string.kCal, it.caloriesBurned.toString().makeShort())
            binding.cheeseburgersBurned.text = it.cheeseburgersBurned.toString().makeShort()
            if (it.waistSizeLoss != 0) {
                binding.totalWaistSizeLayout.isVisible = true
                binding.totalWaistSizeLoss.text = getString(R.string.cm, it.waistSizeLoss.toString().makeShort())
            } else {
                binding.totalWaistSizeLayout.isVisible = false
            }
            if (it.currentWaistSize != 0) {
                binding.waistSizeLayout.isVisible = true
                binding.currentWaistSize.text = getString(R.string.cm, it.currentWaistSize.toString().makeShort())
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
            lineChart.animateX(ANIMATE_DURATION)
            lineChart.data = it.yData
            lineChart.xAxis?.position = XAxis.XAxisPosition.BOTTOM
            lineChart.xAxis?.valueFormatter = IndexAxisValueFormatter(it.xData)
            lineChart.xAxis?.isGranularityEnabled = true
            lineChart.setOnChartValueSelectedListener(this@StatsFragment)
            lineChart.setPinchZoom(true)
            lineChart.description = description
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
            val yAxis = binding.lineChart.axisLeft

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
        private const val ANIMATE_DURATION = 800
    }
}