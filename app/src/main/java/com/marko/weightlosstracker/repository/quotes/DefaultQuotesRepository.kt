package com.marko.weightlosstracker.repository.quotes

import com.marko.weightlosstracker.data.local.dao.QuoteDao
import com.marko.weightlosstracker.data.local.mappers.QuoteMapper
import com.marko.weightlosstracker.data.remote.datasource.QuotesService
import com.marko.weightlosstracker.model.Quote
import com.marko.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultQuotesRepository constructor(
    private val quotesService: QuotesService,
    private val quoteDao: QuoteDao,
    private val quoteMapper: QuoteMapper
) : QuotesRepository {

    override suspend fun fetchQuote(): Flow<DataState<Quote>> = flow {
        emit(DataState.Loading)
        try {
            val response = quotesService.fetchQuote()
            if (response.isSuccessful) {
                response.body()?.let { it ->
                    val quote = quoteMapper.mapFromRemoteEntity(it.first())
                    quoteDao.insertQuote(quoteMapper.mapToEntity(quote))
                    getQuoteLocally()?.let { cachedQuote ->
                        emit(DataState.Success(cachedQuote))
                    } ?: emit(DataState.Error("An unknown error occurred"))
                } ?: emit(DataState.Error("An unknown error occurred"))
            } else {
                emit(DataState.Error("An unknown error occurred"))
            }
        } catch (e: Exception) {
            getQuoteLocally()?.let { cachedQuote ->
                emit(DataState.Success(cachedQuote))
            } ?: emit(DataState.Error(e.message.toString()))
        }
    }

    private suspend fun getQuoteLocally(): Quote? {
        val quotes = quoteDao.getAllQuotes()
        return if (quotes.isNotEmpty()) {
            val randomQuote = quotes.random()
            quoteMapper.mapFromEntity(randomQuote)
        } else null
    }
}