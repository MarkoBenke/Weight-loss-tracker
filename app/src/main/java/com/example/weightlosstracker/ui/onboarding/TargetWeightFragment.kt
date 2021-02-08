package com.example.weightlosstracker.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weightlosstracker.R
import com.example.weightlosstracker.databinding.FragmentTargetWeightBinding
import com.example.weightlosstracker.domain.User
import com.example.weightlosstracker.ui.main.MainActivity
import com.example.weightlosstracker.util.calculateBmi
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TargetWeightFragment: Fragment(R.layout.fragment_target_weight) {

    private var binding: FragmentTargetWeightBinding? = null
    private var user: User? = null
    private val viewModel: TargetWeightViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTargetWeightBinding.bind(view)

        user = requireArguments().getParcelable(OnBoardingActivity.USER_KEY)
        binding?.idealWeight?.text = getString(R.string.ideal_weight_text,
            viewModel.generateIdealWeight(user))

        binding?.submit?.setOnClickListener {
            viewModel.validate(
                binding?.targetWeight?.editText?.text.toString()
            )
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.validateLiveData.observe(viewLifecycleOwner, { success ->
            if (success) {
                val userTargetWeight = binding?.targetWeight?.editText?.text.toString()
                user?.apply {
                    goalName = generateName(userTargetWeight)
                    targetWeight = userTargetWeight.toFloat()
                    startBmi = calculateBmi(currentWeight, height)
                }
                viewModel.insertUserToDb(user)
            } else {
                Snackbar.make(
                    requireView(), getString(R.string.target_weight_error_message),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })

        viewModel.insertUserLiveData.observe(viewLifecycleOwner, {
            if (it) {
                val intent = Intent(requireActivity(), MainActivity::class.java).apply {
                    putExtra(OnBoardingActivity.USER_KEY, user)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(intent)
                requireActivity().finish()
            }
        })
    }

    private fun generateName(targetWeight: String): String {
        return getString(R.string.goal_name, targetWeight)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}