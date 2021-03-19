package com.marko.weightlosstracker.ui.launch

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.marko.weightlosstracker.databinding.ActivitySplashScreenBinding
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.ui.login.LoginActivity
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.ui.onboarding.OnBoardingActivity
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivitySplashScreenBinding::inflate)
    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //TODO add check if remote and local entries are matched, if not updated local from remote
        //TODO change progress bar
        //TODO delete entries
        //TODO check if everything is good when user reinstall the app
        //TODO fix tests
        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        viewModel.isUserSignedInLiveData.observe(this) { dataState ->
            when (dataState) {
                is DataState.Error -> {
                    // User not signed in, navigate to login
                    startLoginScreen()
                }
                is DataState.Success -> {
                    // If user is signed in, get user
                    viewModel.getUser()
                }
                else -> Unit
            }
        }
        viewModel.userLiveData.observe(this, { dataState ->
            when (dataState) {
                is DataState.Loading -> Unit
                is DataState.Success -> {
                    // User is successfully fetched, navigate to home
                    startHomeScreen(dataState.data)
                }
                is DataState.Error -> {
                    // No user in database, navigate to on boarding
                    startOnBoarding()
                }
            }
        })
    }

    private fun startOnBoarding() {
        lifecycleScope.launchWhenStarted {
            delay(SPLASH_TIME_OUT)
            withContext(Dispatchers.Main) {
                startActivity(
                    Intent(
                        this@SplashScreenActivity,
                        LaunchActivity::class.java
                    )
                )
                finish()
            }
        }
    }

    private fun startHomeScreen(userData: User?) {
        lifecycleScope.launchWhenStarted {
            delay(SPLASH_TIME_OUT)
            withContext(Dispatchers.Main) {
                if (userData != null) {
                    val intent =
                        Intent(this@SplashScreenActivity, MainActivity::class.java)
                    intent.putExtra(OnBoardingActivity.USER_KEY, userData)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun startLoginScreen() {
        lifecycleScope.launchWhenStarted {
            delay(SPLASH_TIME_OUT)
            withContext(Dispatchers.Main) {
                startActivity(
                    Intent(
                        this@SplashScreenActivity,
                        LoginActivity::class.java
                    )
                )
                finish()
            }
        }
    }

    companion object {
        const val SPLASH_TIME_OUT: Long = 2500
    }
}