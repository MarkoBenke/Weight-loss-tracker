package com.example.weightlosstracker.repository

import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.repository.quotes.QuotesRepository
import com.example.weightlosstracker.util.DataState
import com.example.weightlosstracker.utils.DataGenerator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeQuotesRepositoryAndroidTest @Inject constructor() : QuotesRepository {

    override suspend fun getQuote(): Flow<DataState<Quote>> = flow {
        emit(DataState.Success(DataGenerator.quote))
    }
}