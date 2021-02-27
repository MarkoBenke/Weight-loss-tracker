package com.marko.weightlosstracker.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentGenderBinding
import com.marko.weightlosstracker.domain.Gender
import com.marko.weightlosstracker.domain.User
import com.marko.weightlosstracker.util.viewBinding
import io.ghyeok.stickyswitch.widget.StickySwitch

class GenderFragment: Fragment(R.layout.fragment_gender) {

    private val binding by viewBinding(FragmentGenderBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = User()
        binding.next.setOnClickListener {
            user.gender = getGenderFromSwitch()
            val bundle = bundleOf(
                OnBoardingActivity.USER_KEY to user
            )
            findNavController().navigate(R.id.action_genderFragment_to_basicInfoFragment, bundle)
        }
    }

    private fun getGenderFromSwitch(): Gender {
        return if (binding.genderSwitch.getDirection() == StickySwitch.Direction.LEFT) {
            Gender.MALE
        } else Gender.FEMALE
    }
}