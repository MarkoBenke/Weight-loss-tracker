package com.marko.weightlosstracker.ui.main.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.marko.weightlosstracker.BuildConfig
import com.marko.weightlosstracker.R
import com.marko.weightlosstracker.databinding.FragmentSettingsBinding
import com.marko.weightlosstracker.ui.core.viewBinding
import com.marko.weightlosstracker.util.Constants

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.version.text = getString(R.string.version, BuildConfig.VERSION_NAME)

        binding.privacy.setOnClickListener {
            openUrl(Constants.PRIVACY_POLICY)
        }

        binding.terms.setOnClickListener {
            openUrl(Constants.TERMS)
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}