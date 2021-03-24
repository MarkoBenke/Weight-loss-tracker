package com.marko.weightlosstracker.ui.main.details

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentEntryDetailsBinding
import com.marko.weightlosstracker.model.WeightEntry
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.dialogs.ErrorDialog
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.ui.main.MainViewModel
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryDetailsFragment : Fragment(R.layout.fragment_entry_details) {

    private val viewModel: EntryDetailsViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
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
            if (mainViewModel.isNetworkAvailable)
                viewModel.validate(binding.newWeightEditText.text.toString())
            else showNoInternetConnectionToast()
        }

        binding.delete.setOnClickListener {
            if (mainViewModel.isNetworkAvailable) viewModel.delete(weightEntry)
            else showNoInternetConnectionToast()
        }

        binding.newWeight.editText?.doOnTextChanged { _, _, _, _ ->
            binding.newWeight.error = ""
        }
    }

    private fun subscribeToObservers() {
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

        viewModel.weightEntryAction.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    binding.progressBar.isVisible = false
                    val dialog = ErrorDialog.newInstance(getString(R.string.unknown_error))
                    dialog.show(parentFragmentManager, ErrorDialog.TAG)
                }
                DataState.Loading -> binding.progressBar.isVisible = true
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    hideKeyboard()
                    findNavController().popBackStack()
                }
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

    private fun showNoInternetConnectionToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.no_internet_connection_error),
            Toast.LENGTH_SHORT
        ).show()
    }
}