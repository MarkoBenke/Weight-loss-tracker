package com.marko.weightlosstracker.ui.main.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentProfileBinding
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.main.MainViewModel
import com.marko.weightlosstracker.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileViewModel, DataState<User?>>(
    R.layout.fragment_profile, ProfileViewModel::class.java
) {

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToUserStatusObserver()
    }

    override fun updateUi(model: DataState<User?>) {
        when (model) {
            is DataState.Success -> {
                binding.targetWeight.editText?.setText(model.data?.targetWeight.toString())
                binding.age.editText?.setText(model.data?.age.toString())
                binding.username.editText?.setText(model.data?.username)
            }
            else -> Unit
        }
    }

    private fun initUi() {
        binding.submit.setOnClickListener {
            viewModel.validate(
                UpdateUserValidationModel(
                    username = binding.usernameEditText.text.toString(),
                    age = binding.ageEditText.text.toString(),
                    targetWeight = binding.targetWeightEditText.text.toString()
                )
            )
        }
    }

    private fun subscribeToUserStatusObserver() {
        viewModel.validateLiveData.observe(viewLifecycleOwner) { dataState ->
            when (dataState) {
                is DataState.Error ->
                    Snackbar.make(
                        requireView(), getString(R.string.mandatory_fields_error_message),
                        Snackbar.LENGTH_LONG
                    ).show()
                DataState.Loading -> Unit
                is DataState.Success -> {
                    if (mainViewModel.isNetworkAvailable) {
                        viewModel.updateUser(dataState.data)
                    } else showNoInternetConnectionToast()
                }
            }
        }

        viewModel.updateUserLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is DataState.Error -> {
                    showErrorDialog()
                    binding.progressBar.isVisible = false
                }
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    mainViewModel.userLiveData.value?.targetWeight =
                        binding.targetWeightEditText.text.toString().toFloat()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.user_update_success),
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }
                is DataState.Loading -> binding.progressBar.isVisible = true
            }
        })
    }
}