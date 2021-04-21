package com.marko.weightlosstracker.ui.main.stats

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentStatsBinding
import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.util.Constants
import com.marko.weightlosstracker.util.roundUp
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.marko.weightlosstracker.ui.core.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

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
            Toast.makeText(requireContext(), getString(R.string.unknown_error_title), Toast.LENGTH_SHORT)
                .show()
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
                        binding.chart.data.setValueTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.primaryTextColor
                            )
                        )
                    }
                }
            }
        }
    }

    private fun initStatsUi(stats: Stats?) {
        stats?.let {
            binding.currentWeight.text = getString(R.string.kg, it.getCurrentWeightString())
            binding.targetWeight.text = getString(R.string.kg, it.getTargetWeightString())
            binding.bmiCategory.text = getBmiCategory(it.bmi)
            if (it.remaining < 0) {
                binding.remaining.text = getString(R.string.goal_accomplished)
            } else {
                binding.remaining.text = getString(R.string.kg, it.getRemainingString())
            }
            if (it.totalLoss < 0) {
                binding.totalLoss.text =
                    getString(R.string.kg_plus, abs(it.totalLoss.roundUp()).toString())
            } else {
                binding.totalLoss.text = getString(R.string.kg, it.getTotalLossString())
            }
            if (it.caloriesBurned < 0) {
                binding.caloriesBurned.text =
                    getString(R.string.kCal_plus, abs(it.caloriesBurned.roundUp()).toString())
            } else {
                binding.caloriesBurned.text = getString(R.string.kCal, it.getCaloriesBurnedString())
            }
            if (it.cheeseburgersBurned < 0) {
                binding.cheeseburgersBurned.text =
                    getString(R.string.plus, abs(it.cheeseburgersBurned.roundUp()).toString())
            } else {
                binding.cheeseburgersBurned.text = it.getCheeseburgersBurnedString()
            }
            if (it.waistSizeLoss != 0) {
                binding.totalWaistSizeLayout.isVisible = true
                if (it.waistSizeLoss > 0) {
                    binding.totalWaistSizeLoss.text =
                        getString(R.string.cm_plus, it.waistSizeLoss.toString())
                } else {
                    binding.totalWaistSizeLoss.text =
                        getString(R.string.cm, it.waistSizeLoss.toString())
                }
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
        setLabelsTextColor()
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

    private fun initChartLimitLines(stats: Stats?) {
        stats?.let {
            val startWeightLine = viewModel.createLimitLine(
                getString(R.string.start_weight_chart),
                it.startWeight
            )
            startWeightLine.textColor =
                ContextCompat.getColor(requireContext(), R.color.primaryTextColor)
            startWeightLine.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
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