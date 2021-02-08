package com.example.weightlosstracker.ui.main.add

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentAddEntryBinding
import com.example.weightlosstracker.domain.WeightEntry
import com.example.weightlosstracker.util.getCurrentDate
import com.example.weightlosstracker.util.parseSelectedDate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddEntryFragment : Fragment(R.layout.fragment_add_entry) {

    private var binding: FragmentAddEntryBinding? = null
    private val viewModel: AddEntryViewModel by viewModels()
    private var calendar = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddEntryBinding.bind(view)

        initSubmit()
        subscribeToObservers()
    }

    private fun initSubmit() {
        binding?.submitBtn?.setOnClickListener {
            viewModel.validate(
                binding?.newWeight?.editText?.text.toString()
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.startDateLiveData.observe(viewLifecycleOwner, { startDateInMillis ->
            initDateCalendar(startDateInMillis)
        })
        viewModel.validateLiveData.observe(viewLifecycleOwner, { success ->
            if (success) {
                viewModel.insertNewEntry(
                    WeightEntry(
                        currentWeight = binding?.newWeight?.editText?.text?.toString()!!.toFloat(),
                        waistSize = binding?.waistSize?.editText?.text?.toString()!!.toIntOrNull()
                            ?: 0,
                        date = binding?.setDateText?.text.toString(),
                        description = binding?.description?.editText?.text.toString(),
                    )
                )
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.current_weight_error_message),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })

        viewModel.insertWeightLiveData.observe(viewLifecycleOwner, {
            if (it) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.entry_success),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun initDateCalendar(startDateInMillis: Long) {
        binding?.setDateText?.text = getCurrentDate()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding?.setDateText?.text = parseSelectedDate(calendar.time)
            }

        binding?.setDate?.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireActivity(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.datePicker.minDate = startDateInMillis
            datePickerDialog.show()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}