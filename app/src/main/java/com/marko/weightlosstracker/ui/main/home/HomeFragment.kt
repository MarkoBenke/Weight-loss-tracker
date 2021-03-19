package com.marko.weightlosstracker.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentHomeBinding
import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.dialogs.ErrorDialog
import com.marko.weightlosstracker.util.*
import dagger.hilt.android.AndroidEntryPoint
import mobi.gspd.segmentedbarview.Segment

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, Stats>(
    R.layout.fragment_home,
    HomeViewModel::class.java
) {

    private val binding by viewBinding(FragmentHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToQuoteObserver()
    }

    override fun updateUi(model: Stats) {
        initUi(model)
    }

    private fun subscribeToQuoteObserver() {
        viewModel.quoteLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is DataState.Success -> {
                    binding.quoteText.text = it.data.quote
                    binding.quoteAuthor.text = it.data.author
                    binding.progressBar.isVisible = false
                }
                is DataState.Error -> {
                    binding.progressBar.isVisible = false
                    val dialog = ErrorDialog.newInstance(
                        if (it.message.isEmpty()) getString(R.string.unknown_error)
                        else it.message
                    )
                    dialog.show(parentFragmentManager, ErrorDialog.TAG)
                }
                DataState.Loading -> {
                    binding.progressBar.isVisible = true
                }
            }
        })
    }

    private fun initUi(stats: Stats) {
        setupBmiSegments(stats)
        with(binding) {
            currentBmi.text = getString(R.string.bmi_details_card, stats.bmi.shortToString())
            currentWeight.text = getString(R.string.kg, stats.currentWeight.shortToString())
            currentDate.text = stats.lastEntryDate
            goalName.text = getGoalName(stats.targetWeight.toString())
            startWeight.text = getString(R.string.kg, stats.startWeight.toString())
            startBmi.text =
                getString(R.string.bmi_details_card, stats.startBmi.shortToString())
            startDate.text = stats.startDate
        }
    }

    private fun setupBmiSegments(stats: Stats) {
        with(binding) {
            segments.setSegments(generateItemList())
            segments.valueSegment = getBmiType(stats.bmi)
            segments.setValueWithUnit(stats.bmi.short(), getString(R.string.bmi_label))
        }
    }

    private fun generateItemList(): ArrayList<Segment> {
        val itemList = arrayListOf<Segment>()

        return itemList.apply {
            clear()
            add(
                Segment(
                    Constants.UNDERWEIGHT_START,
                    context?.getString(R.string.bmi_type_underweight),
                    ContextCompat.getColor(requireContext(), R.color.segment_one)
                )
            )
            add(
                Segment(
                    Constants.NORMAL_START,
                    Constants.NORMAL_END,
                    context?.getString(R.string.bmi_type_normal),
                    ContextCompat.getColor(requireContext(), R.color.segment_two)
                )
            )
            add(
                Segment(
                    Constants.NORMAL_END,
                    Constants.OVERWEIGHT,
                    context?.getString(R.string.bmi_type_overweight),
                    ContextCompat.getColor(requireContext(), R.color.segment_three)
                )
            )
            add(
                Segment(
                    Constants.OBESE_END,
                    context?.getString(R.string.bmi_type_obese),
                    ContextCompat.getColor(requireContext(), R.color.segment_four)
                )
            )
        }
    }

    private fun getGoalName(targetWeight: String): String {
        return getString(R.string.goal_name, targetWeight)
    }
}