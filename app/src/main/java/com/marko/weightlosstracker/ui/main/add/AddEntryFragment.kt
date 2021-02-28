package com.marko.weightlosstracker.ui.main.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentAddEntryBinding
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.util.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        subscribeToInsertWeightEntryObserver()
    }

    override fun updateUi(model: Long) {
        initDateCalendar(model)
    }

    private fun initListeners() {
        binding.newWeight.editText?.doOnTextChanged { _, _, _, _ ->
            binding.newWeight.error = ""
        }

        binding.submitBtn.setOnClickListener {
            val newWeight = binding.newWeight.editText?.text.toString()
            viewModel.insertNewEntry(
                newWeight,
                WeightEntry(
                    uuid = binding.setDateText.text.toString().replace(".", ""),
                    currentWeight = newWeight.toFloatOrNull() ?: 0f,
                    waistSize = binding.waistSize.editText?.text.toString().toIntOrNull() ?: 0,
                    date = binding.setDateText.text.toString(),
                    description = binding.description.editText?.text.toString(),
                )
            )
        }
    }

    private fun subscribeToInsertWeightEntryObserver() {
        viewModel.insertWeightLiveData.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is DataState.Success -> {
                        clearFields()
                        Toast.makeText(requireContext(), R.string.entry_success, Toast.LENGTH_LONG)
                            .show()
                    }
                    is DataState.Error -> {
                        binding.newWeight.error = getString(R.string.mandatory_field)
                    }
                    DataState.Loading -> {
                        /* NO-OP */
                    }
                }
            }
        })
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