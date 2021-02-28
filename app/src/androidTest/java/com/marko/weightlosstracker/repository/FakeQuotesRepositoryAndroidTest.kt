package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.model.Quote
import com.marko.weightlosstracker.repository.quotes.QuotesRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.utils.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeQuotesRepositoryAndroidTest @Inject constructor() : QuotesRepository {

    override suspend fun fetchQuote(): Flow<DataState<Quote>> = flow {
        emit(DataState.Success(DataGenerator.quote))
    }
}