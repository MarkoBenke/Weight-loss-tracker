package com.example.weightlosstracker.ui.main.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.weightlosstracker.BuildConfig
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentInfoBinding
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.util.BaseFragment
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<InfoViewModel, DataState<User?>>(
    R.layout.fragment_info, InfoViewModel::class.java
) {

    private val privacyPolicy = "https://sites.google.com/view/weightlotracker/home"
    private val terms = "https://sites.google.com/view/weightlotracker/terms?authuser=0"
    private val binding by viewBinding(FragmentInfoBinding::bind)

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
            is DataState.Loading -> {
                /* NO-OP */
            }
            is DataState.Success -> {
                binding.updateTargetWeight.editText?.setText(model.data?.targetWeight.toString())
            }
        }
    }

    private fun initUi() {
        binding.version.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        binding.updateTargetWeight.setEndIconOnClickListener {
            val updatedTargetWeight = binding.updateTargetWeightEditText.text.toString().toFloat()
            viewModel.updateUser(updatedTargetWeight)
        }

        binding.privacy.setOnClickListener {
            openUrl(privacyPolicy)
        }

        binding.terms.setOnClickListener {
            openUrl(terms)
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun subscribeToUserStatusObserver() {
        viewModel.updateUserLiveData.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.target_weight_update_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}