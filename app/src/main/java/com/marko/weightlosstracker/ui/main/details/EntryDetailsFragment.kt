package com.marko.weightlosstracker.ui.main.details

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentEntryDetailsBinding
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryDetailsFragment : Fragment(R.layout.fragment_entry_details) {

    private val viewModel: EntryDetailsViewModel by viewModels()
    private val binding by viewBinding(FragmentEntryDetailsBinding::bind)
    private lateinit var weightEntry: WeightEntry

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initListeners()
        subscribeToObservers()
    }

    private fun initListeners() {
        binding.submit.setOnClickListener {
            viewModel.validate(binding.newWeightEditText.text.toString())
        }

        binding.delete.setOnClickListener {
            viewModel.delete(weightEntry)
        }

        binding.newWeight.editText?.doOnTextChanged { _, _, _, _ ->
            binding.newWeight.error = ""
        }
    }

    private fun subscribeToObservers() {
        viewModel.weightEntryAction.observe(viewLifecycleOwner) { actionCompleted ->
            if (actionCompleted) {
                hideKeyboard()
                findNavController().popBackStack()
            }
        }

        viewModel.validation.observe(viewLifecycleOwner) { result ->
            when (result) {
                is DataState.Error -> binding.newWeight.error = getString(R.string.mandatory_field)
                is DataState.Success -> {
                    weightEntry.apply {
                        currentWeight =
                            binding.newWeightEditText.text.toString().toFloatOrNull() ?: 0f
                        waistSize = binding.waistSizeEditText.text.toString().toIntOrNull() ?: 0
                        description = binding.descriptionEditText.text.toString()
                    }
                    viewModel.update(weightEntry)
                }
                else -> Unit
            }
        }
    }

    private fun initUi() {
        weightEntry = requireArguments().getParcelable(MainActivity.WEIGHT_ENTRY_KEY)!!
        with(binding) {
            if (weightEntry.isInitialEntry) delete.isEnabled = false
            newWeight.editText?.setText(weightEntry.currentWeight.toString())
            waistSize.editText?.setText(weightEntry.waistSize.toString())
            description.editText?.setText(weightEntry.description)
        }
    }
}