package com.marko.weightlosstracker.ui.main.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentAddEntryBinding
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.dialogs.ErrorDialog
import com.marko.weightlosstracker.ui.main.MainViewModel
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.getCurrentDate
import com.marko.weightlosstracker.util.parseSelectedDate
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddEntryFragment : BaseFragment<AddEntryViewModel, Long>(
    R.layout.fragment_add_entry,
    AddEntryViewModel::class.java
) {

    private val binding by viewBinding(FragmentAddEntryBinding::bind)
    private var calendar = Calendar.getInstance()
    private lateinit var datePickerDialog: DatePickerDialog
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        subscribeToObservers()
    }

    override fun updateUi(model: Long) {
        initDateCalendar(model)
    }

    private fun initListeners() {
        binding.newWeight.editText?.doOnTextChanged { _, _, _, _ ->
            binding.newWeight.error = ""
        }

        binding.submitBtn.setOnClickListener {
            if (mainViewModel.isNetworkAvailable) {
                val newWeight = binding.newWeight.editText?.text.toString()
                viewModel.validate(newWeight)
            } else {
                showNoInternetConnectionToast()
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.validationLiveData.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is DataState.Success -> {
                        viewModel.insertWeightEntry(
                            WeightEntry(
                                uuid = binding.setDateText.text.toString().replace(".", ""),
                                currentWeight = result.data.toFloatOrNull() ?: 0f,
                                waistSize = binding.waistSize.editText?.text.toString()
                                    .toIntOrNull() ?: 0,
                                date = binding.setDateText.text.toString(),
                                description = binding.description.editText?.text.toString(),
                            )
                        )
                    }
                    is DataState.Error -> {
                        binding.newWeight.error = getString(R.string.mandatory_field)
                    }
                    DataState.Loading -> Unit
                }
            }
        })

        viewModel.insertWeightLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    binding.progressBar.isVisible = false
                    val dialog = ErrorDialog.newInstance(getString(R.string.unknown_error))
                    dialog.show(parentFragmentManager, ErrorDialog.TAG)
                }
                DataState.Loading -> binding.progressBar.isVisible = true
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    clearFields()
                    Toast.makeText(requireContext(), R.string.entry_success, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    private fun clearFields() {
        with(binding) {
            newWeight.editText?.text?.clear()
            description.editText?.text?.clear()
            waistSize.editText?.text?.clear()
            setDateText.text = getCurrentDate()
        }
    }

    private fun initDateCalendar(startDateInMillis: Long) {
        binding.setDateText.text = getCurrentDate()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.setDateText.text = parseSelectedDate(calendar.time)
            }

        datePickerDialog = DatePickerDialog(
            requireActivity(), dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.datePicker.minDate = startDateInMillis
        binding.setDate.setOnClickListener {
            datePickerDialog.show()
        }
        binding.setDateText.setOnClickListener {
            datePickerDialog.show()
        }
    }
}