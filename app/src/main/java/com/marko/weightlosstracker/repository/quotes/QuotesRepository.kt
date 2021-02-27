package com.marko.weightlosstracker.repository.quotes

import com.marko.weightlosstracker.domain.Quote
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun getQuote(): Flow<DataState<Quote>>
}