package com.marko.weightlosstracker.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.ActivityMainBinding
import com.marko.weightlosstracker.ui.core.ConnectivityManager
import com.marko.weightlosstracker.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val viewModel: MainViewModel by viewModels()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigation()

        initConnectivityManager()
    }

    private fun initConnectivityManager() {
        connectivityManager.registerConnectionObserver(this)
        connectivityManager.isNetworkAvailable.observe(this) { isNetworkAvailable ->
            binding.networkStatus.isVisible = !isNetworkAvailable
            viewModel.isNetworkAvailable = isNetworkAvailable
        }
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment

        navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        binding.navigationView.setupWithNavController(navController)
        binding.navigationView.itemIconTintList = null

        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)

        setupActionBarWithNavController(this, navController, binding.drawerLayout)

        viewModel.userLiveData.observe(this) {
            supportActionBar?.title = getString(
                R.string.goal_name,
                it?.targetWeight.toString()
            )
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.entryDetailsFragment, R.id.profileFragment, R.id.settingsFragment ->
                    binding.bottomNav.isVisible = false
                R.id.home -> {
                    binding.bottomNav.isVisible = true
                    supportActionBar?.title = getString(
                        R.string.goal_name,
                        viewModel.userLiveData.value?.targetWeight.toString()
                    )
                }
                else -> binding.bottomNav.isVisible = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(navController, appBarConfiguration)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    companion object {
        const val WEIGHT_ENTRY_KEY = "weightEntry"
    }
}