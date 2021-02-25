package com.example.weightlosstracker.util

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

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

    private fun subscribeToObservers() {
        viewModel.modelLiveData.observe(viewLifecycleOwner, {
            updateUi(it)
        })
    }
}