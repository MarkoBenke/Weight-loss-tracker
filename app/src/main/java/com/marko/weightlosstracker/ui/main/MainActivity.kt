package com.marko.weightlosstracker.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.ActivityMainBinding
import com.marko.weightlosstracker.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        binding.bottomNav.setupWithNavController(navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id) {
                R.id.entryDetailsFragment -> binding.bottomNav.isVisible = false
                else -> binding.bottomNav.isVisible = true
            }
        }
    }

    companion object {
        const val WEIGHT_ENTRY_KEY = "weightEntry"
    }
}