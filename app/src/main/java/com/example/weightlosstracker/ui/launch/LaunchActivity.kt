package com.example.weightlosstracker.ui.launch

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.weightlosstracker.databinding.ActivityLaunchBinding
import com.example.weightlosstracker.ui.onboarding.OnBoardingActivity

class LaunchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLaunchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLaunchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        binding.startButton.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
        }
    }
}