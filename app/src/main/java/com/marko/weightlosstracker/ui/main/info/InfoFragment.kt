package com.marko.weightlosstracker.ui.main.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.marko.weightlosstracker.BuildConfig
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentInfoBinding
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.main.MainViewModel
import com.marko.weightlosstracker.util.Constants
import com.marko.weightlosstracker.util.DataState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<InfoViewModel, DataState<User?>>(
    R.layout.fragment_info, InfoViewModel::class.java
) {

    private val binding by viewBinding(FragmentInfoBinding::bind)
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToUserStatusObserver()
    }

    override fun updateUi(model: DataState<User?>) {
        when (model) {
            is DataState.Error -> {
                binding.updateTargetWeight.editText?.setText(getString(R.string.unknown_error))
            }
            is DataState.Loading -> Unit
            is DataState.Success -> {
                binding.updateTargetWeight.editText?.setText(model.data?.targetWeight.toString())
            }
        }
    }

    private fun initUi() {
        binding.version.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        binding.updateTargetWeight.setEndIconOnClickListener {
            if (mainViewModel.isNetworkAvailable) {
                val updatedTargetWeight = binding.updateTargetWeightEditText.text.toString()
                viewModel.updateUser(updatedTargetWeight)
            } else showNoInternetConnectionToast()
        }

        binding.privacy.setOnClickListener {
            openUrl(Constants.PRIVACY_POLICY)
        }

        binding.terms.setOnClickListener {
            openUrl(Constants.TERMS)
        }

        binding.updateTargetWeight.editText?.doOnTextChanged { _, _, _, _ ->
            binding.updateTargetWeight.error = ""
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun subscribeToUserStatusObserver() {
        viewModel.updateUserLiveData.observe(viewLifecycleOwner, { result ->
            when (result) {
                is DataState.Error -> {
                    binding.progressBar.isVisible = false
                    binding.updateTargetWeight.error =
                        getString(R.string.mandatory_field)
                }
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.target_weight_update_success),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DataState.Loading -> binding.progressBar.isVisible = true
            }
        })
    }
}