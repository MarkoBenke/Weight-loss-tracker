package com.marko.weightlosstracker.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentTargetWeightBinding
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.ui.core.BaseFragment
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.ui.main.MainViewModel
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.Event
import com.marko.weightlosstracker.util.calculateBmi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetWeightFragment : BaseFragment<TargetWeightViewModel, Event<DataState<Unit>>>(
    R.layout.fragment_target_weight,
    TargetWeightViewModel::class.java
) {

    private val binding by viewBinding(FragmentTargetWeightBinding::bind)
    private var user: User? = null
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    override fun updateUi(model: Event<DataState<Unit>>) {
        model.getContentIfNotHandled()?.let { result ->
            when (result) {
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                        putExtra(OnBoardingActivity.USER_KEY, user)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }
                is DataState.Error -> {
                    //TODO check if this works when you get error state more than once
                    binding.progressBar.isVisible = false
                    binding.targetWeight.error = getString(R.string.mandatory_field)
                }
                DataState.Loading -> binding.progressBar.isVisible = true
            }
        }
    }

    private fun initUi() {
        user = requireArguments().getParcelable(OnBoardingActivity.USER_KEY)
        binding.idealWeight.text = getString(
            R.string.ideal_weight_text,
            user?.username,
            viewModel.generateIdealWeight(user)
        )

        binding.submit.setOnClickListener {
            if (mainViewModel.isNetworkAvailable) createUser()
            else showNoInternetConnectionToast()
        }

        binding.targetWeight.editText?.doOnTextChanged { _, _, _, _ ->
            binding.targetWeight.error = ""
        }
    }

    private fun createUser() {
        val userTargetWeight = binding.targetWeight.editText?.text.toString()
        user?.apply {
            goalName = generateName(userTargetWeight)
            targetWeight = userTargetWeight.toFloatOrNull() ?: 0f
            startBmi = calculateBmi(currentWeight, height)
        }

        viewModel.createUser(userTargetWeight, user)
    }

    private fun generateName(targetWeight: String): String {
        return getString(R.string.goal_name, targetWeight)
    }
}