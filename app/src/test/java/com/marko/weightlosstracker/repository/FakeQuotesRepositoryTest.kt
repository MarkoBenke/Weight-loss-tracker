package com.marko.weightlosstracker.repository

import com.marko.weightlosstracker.model.Quote
import com.marko.weightlosstracker.repository.quotes.QuotesRepository
import com.marko.weightlosstracker.util.DataState
import com.marko.weightlosstracker.other.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeQuotesRepositoryTest @Inject constructor(
    private var shouldReturnError: Boolean = false
) : QuotesRepository {

    override suspend fun fetchQuote(): Flow<DataState<Quote>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error("An unknown error occurred"))
            return@flow
        }
        emit(DataState.Success(DataGenerator.quote))
    }
}