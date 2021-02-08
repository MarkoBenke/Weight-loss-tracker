package com.example.weightlosstracker.ui.launch

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.weightlosstracker.databinding.ActivitySplashScreenBinding
import com.example.weightlosstracker.ui.main.MainActivity
import com.example.weightlosstracker.ui.onboarding.OnBoardingActivity
import com.example.weightlosstracker.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()
    private val SPLASH_TIME_OUT: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.userLiveData.observe(this, { dataState ->
            when (dataState) {
                is DataState.Loading -> {

                }
                is DataState.Success -> {
                    lifecycleScope.launchWhenStarted {
                        delay(SPLASH_TIME_OUT)
                        withContext(Dispatchers.Main) {
                            if (dataState.data != null) {
                                val intent =
                                    Intent(this@SplashScreenActivity, MainActivity::class.java)
                                intent.putExtra(OnBoardingActivity.USER_KEY, dataState.data)
                                startActivity(intent)
                            } else {
                                startActivity(
                                    Intent(
                                        this@SplashScreenActivity,
                                        LaunchActivity::class.java
                                    )
                                )
                            }
                            finish()
                        }
                    }
                }
                is DataState.Error -> {

                }
            }
        })
    }
}