package com.marko.weightlosstracker.ui.core

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.marko.weightlosstracker.R

abstract class BaseFragment<T : BaseViewModel<BaseModelData>, BaseModelData>(
    layoutId: Int, private val viewModelClass: Class<T>
) : Fragment(layoutId) {

    internal lateinit var viewModel: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(viewModelClass)
        viewModel.fetchInitialData()
        subscribeToObservers()
    }

    abstract fun updateUi(model: BaseModelData)

    fun showNoInternetConnectionToast() {
        Toast.makeText(
            requireContext(),
            getString(R.string.no_internet_connection_error),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun subscribeToObservers() {
        viewModel.modelLiveData.observe(viewLifecycleOwner, {
            updateUi(it)
        })
    }
}