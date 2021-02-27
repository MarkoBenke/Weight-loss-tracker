package com.marko.weightlosstracker.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marko.weightlosstracker.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
    }

    companion object {
        const val USER_KEY = "user_key"
    }
}