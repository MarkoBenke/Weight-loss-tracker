package com.example.weightlosstracker.repository

import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.repository.qoutes.QuotesRepository
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeQuotesRepository: QuotesRepository {

    override suspend fun getQuote(): Flow<DataState<Quote>> = flow {
        emit(DataState.Success(Quote(
            1, "Marko", "Benke", "KIDAJ!!!"
        )))
    }
}