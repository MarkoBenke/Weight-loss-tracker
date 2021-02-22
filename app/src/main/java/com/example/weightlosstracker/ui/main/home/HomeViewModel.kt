package com.example.weightlosstracker.ui.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.domain.Stats
import com.example.weightlosstracker.repository.quotes.QuotesRepository
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import com.example.weightlosstracker.util.BaseViewModel
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
            quotesRepository.getQuote().collect {
                withContext(dispatcherProvider.main) {
                    quoteLiveData.postValue(it)
                }
            }
        }
    }
}