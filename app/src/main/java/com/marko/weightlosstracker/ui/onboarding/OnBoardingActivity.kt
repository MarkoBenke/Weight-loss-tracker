package com.marko.weightlosstracker.ui.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.ui.core.ConnectivityManager
import com.marko.weightlosstracker.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)

        initConnectivityManager()
    }

    private fun initConnectivityManager() {
        connectivityManager.registerConnectionObserver(this)
        connectivityManager.isNetworkAvailable.observe(this) { isNetworkAvailable ->
            viewModel.isNetworkAvailable = isNetworkAvailable
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    companion object {
        const val USER_KEY = "user_key"
    }
}