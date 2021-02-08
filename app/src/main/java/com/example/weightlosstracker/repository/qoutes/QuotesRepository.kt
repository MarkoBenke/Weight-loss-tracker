package com.example.weightlosstracker.repository.qoutes

import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    suspend fun getQuote(): Flow<DataState<Quote>>
}