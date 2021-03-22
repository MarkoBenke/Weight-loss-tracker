package com.marko.weightlosstracker.ui.onboarding

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentBasicInfoBinding
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.getCurrentDate
import com.marko.weightlosstracker.util.parseSelectedDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BasicInfoFragment : Fragment(R.layout.fragment_basic_info) {

    private val binding by viewBinding(FragmentBasicInfoBinding::bind)
    private val viewModel: BasicInfoViewModel by viewModels()
    private var user: User? = null
    private var calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.next.setOnClickListener {
            viewModel.validate(
                BasicInfoValidationModel(
                    binding.userName.editText?.text.toString(),
                    binding.height.editText?.text.toString(),
                    binding.age.editText?.text.toString(),
                    binding.currentWeight.editText?.text.toString(),
                )
            )
        }

        initDateCalendar()
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        user = requireArguments().getParcelable(OnBoardingActivity.USER_KEY)
        viewModel.validateLiveData.observe(viewLifecycleOwner, { event ->
            when (event?.getContentIfNotHandled()) {
                is DataState.Success -> {
                    user?.apply {
                        height = binding.height.editText?.text.toString().toFloat()
                        username = binding.userName.editText?.text.toString()
                        age = binding.age.editText?.text.toString().toInt()
                        currentWeight =
                            binding.currentWeight.editText?.text.toString().toFloat()
                        startWaistSize =
                            binding.waistSize.editText?.text.toString().toIntOrNull() ?: 0
                        startWeight = currentWeight
                        startDate = binding.setDateText.text.toString()
                    }
                    val bundle = bundleOf(
                        OnBoardingActivity.USER_KEY to user
                    )
                    findNavController().navigate(
                        R.id.action_basicInfoFragment_to_targetWeightFragment,
                        bundle
                    )
                }
                is DataState.Error -> {
                    Snackbar.make(
                        requireView(), getString(R.string.mandatory_fields_error_message),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                DataState.Loading -> Unit
            }
        })
    }

    private fun initDateCalendar() {
        binding.setDateText.text = getCurrentDate()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.setDateText.text = parseSelectedDate(calendar.time)
            }

        val datePickerDialog = DatePickerDialog(
            requireActivity(), dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        binding.setDate.setOnClickListener {
            datePickerDialog.show()
        }

        binding.setDateText.setOnClickListener {
            datePickerDialog.show()
        }
    }
}