package com.example.weightlosstracker.repository

import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.repository.quotes.QuotesRepository
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.other.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeQuotesRepositoryTest @Inject constructor(
    private var shouldReturnError: Boolean = false
) : QuotesRepository {

    override suspend fun getQuote(): Flow<DataState<Quote>> = flow {
        if (shouldReturnError) {
            emit(DataState.Error("An unknown error occurred"))
            return@flow
        }
        emit(DataState.Success(DataGenerator.quote))
    }
}