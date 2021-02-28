package com.marko.weightlosstracker.repository.quotes

import com.marko.weightlosstracker.model.Quote
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun fetchQuote(): Flow<DataState<Quote>>
}