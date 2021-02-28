package com.marko.weightlosstracker.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.marko.weightlosstracker.model.Quote
import com.marko.weightlosstracker.model.Stats
import com.marko.weightlosstracker.repository.quotes.QuotesRepository
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.marko.weightlosstracker.ui.core.BaseViewModel
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.ui.core.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository,
    private val weightEntryRepository: WeightEntryRepository,
    private val dispatcherProvider: DispatcherProvider
): BaseViewModel<Stats>() {

    val quoteLiveData = MutableLiveData<DataState<Quote>>()

    override fun fetchInitialData() {
        getQuote()
        getUserStats()
    }

    private fun getUserStats() {
        viewModelScope.launch(dispatcherProvider.io) {
            weightEntryRepository.getUserStats().collect {
                modelLiveData.postValue(it)
            }
        }
    }

    private fun getQuote() {
        viewModelScope.launch(dispatcherProvider.io) {
            quotesRepository.fetchQuote().collect {
                withContext(dispatcherProvider.main) {
                    quoteLiveData.postValue(it)
                }
            }
        }
    }
}