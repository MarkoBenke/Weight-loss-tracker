package com.marko.weightlosstracker.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.firebase.auth.GoogleAuthProvider
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.ActivityLoginBinding
import com.marko.weightlosstracker.model.User
import com.marko.weightlosstracker.ui.dialogs.ErrorDialog
import com.marko.weightlosstracker.ui.launch.LaunchActivity
import com.marko.weightlosstracker.ui.login.result.AuthResultContract
import com.marko.weightlosstracker.ui.main.MainActivity
import com.marko.weightlosstracker.ui.onboarding.OnBoardingActivity
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityLoginBinding::inflate)
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        subscribeToObservers()
        binding.googleSignIn.setOnClickListener {
            googleSignIn()
        }
    }

    private fun subscribeToObservers() {
        viewModel.userLiveData.observe(this, { dataState ->
            when (dataState) {
                is DataState.Loading -> Unit
                is DataState.Success -> startHomeScreen(dataState.data)
                is DataState.Error -> startOnBoarding()
            }
        })

        viewModel.authUserLiveData.observe(this) { result ->
            when (result) {
                is DataState.Error -> {
                    binding.progressBar.isVisible = false
                    val dialog = ErrorDialog.newInstance(
                        if (result.message.isEmpty()) getString(R.string.unknown_error)
                        else result.message
                    )
                    dialog.show(supportFragmentManager, ErrorDialog.TAG)
                }
                is DataState.Success -> {
                    binding.progressBar.isVisible = false
                    viewModel.getUser()
                }
                DataState.Loading -> binding.progressBar.isVisible = true
            }
        }
    }

    private fun startOnBoarding() {
        startActivity(Intent(this, LaunchActivity::class.java))
        finish()
    }

    private fun startHomeScreen(userData: User?) {
        if (userData != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(OnBoardingActivity.USER_KEY, userData)
            startActivity(intent)
            finish()
        }
    }

    private fun googleSignIn() {
        authResultLauncher.launch(RC_SIGN_IN)
    }

    private val authResultLauncher = registerForActivityResult(AuthResultContract()) { token ->
        handleAuthResponse(token)
    }

    private fun handleAuthResponse(token: String?) {
        if (token != null) {
            val credential = GoogleAuthProvider.getCredential(token, null)
            viewModel.loginWithGoogle(credential)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 101
    }
}