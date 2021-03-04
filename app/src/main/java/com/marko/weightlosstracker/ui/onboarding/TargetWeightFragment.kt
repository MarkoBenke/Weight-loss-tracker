package com.marko.weightlosstracker.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentTargetWeightBinding
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.calculateBmi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetWeightFragment : Fragment(R.layout.fragment_target_weight) {

    private val binding by viewBinding(FragmentTargetWeightBinding::bind)
    private var user: User? = null
    private val viewModel: TargetWeightViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        subscribeToObservers()
    }

    private fun initUi() {
        user = requireArguments().getParcelable(OnBoardingActivity.USER_KEY)
        binding.idealWeight.text = getString(
            R.string.ideal_weight_text,
            viewModel.generateIdealWeight(user)
        )

        binding.submit.setOnClickListener {
            val userTargetWeight = binding.targetWeight.editText?.text.toString()
            user?.apply {
                goalName = generateName(userTargetWeight)
                targetWeight = userTargetWeight.toFloatOrNull() ?: 0f
                startBmi = calculateBmi(currentWeight, height)
            }

            viewModel.insertUserToDb(userTargetWeight, user)
        }

        binding.targetWeight.editText?.doOnTextChanged { _, _, _, _ ->
            binding.targetWeight.error = ""
        }
    }

    private fun subscribeToObservers() {
        viewModel.insertUserLiveData.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result) {
                    is DataState.Success -> {
                        val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                            putExtra(OnBoardingActivity.USER_KEY, user)
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                        startActivity(intent)
                        requireActivity().finish()
                    }
                    is DataState.Error -> {
                        binding.targetWeight.error = getString(R.string.mandatory_field)
                    }
                    DataState.Loading -> Unit
                }
            }
        })
    }

    private fun generateName(targetWeight: String): String {
        return getString(R.string.goal_name, targetWeight)
    }
}