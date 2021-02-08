package com.example.weightlosstracker.repository.qoutes

import com.example.weightlosstracker.data.local.dao.QuoteDAO
import com.example.weightlosstracker.data.local.mappers.QuoteLocalMapper
import com.example.weightlosstracker.data.remote.QuoteNetworkMapper
import com.example.weightlosstracker.data.remote.QuotesApi
import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultQuotesRepository constructor(
    private val quotesApi: QuotesApi,
    private val quoteDAO: QuoteDAO,
    private val quoteNetworkMapper: QuoteNetworkMapper,
    private val quoteLocalMapper: QuoteLocalMapper
) : QuotesRepository {

    override suspend fun getQuote(): Flow<DataState<Quote>> = flow {
        emit(DataState.Loading)
        try {
            val response = quotesApi.getQuoteOfDay()
            if (response.isSuccessful) {
                response.body()?.let {
                    val quote = quoteNetworkMapper.mapFromEntity(it.first())
                    quoteDAO.insertQuote(quoteLocalMapper.mapToEntity(quote))
                    val quotes = quoteDAO.getAllQuotes()
                    if (quotes.isNotEmpty()) {
                        val randomQuote = quotes.random()
                        emit(DataState.Success(quoteLocalMapper.mapFromEntity(randomQuote)))
                    }
                } ?: emit(DataState.Error("An unknown error occurred"))
            } else {
                emit(DataState.Error("An unknown error occurred"))
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.message.toString()))
        }
    }
}