package com.marko.weightlosstracker.ui.launch

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.marko.weightlosstracker.databinding.ActivityLaunchBinding
import com.marko.weightlosstracker.ui.onboarding.OnBoardingActivity
import com.marko.weightlosstracker.util.viewBinding

class LaunchActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLaunchBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //Todo not working for api 30
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
    }
}