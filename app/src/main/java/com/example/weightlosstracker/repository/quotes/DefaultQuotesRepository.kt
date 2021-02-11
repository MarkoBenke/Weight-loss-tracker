package com.example.weightlosstracker.repository.quotes

import com.example.weightlosstracker.data.local.dao.QuoteDao
import com.example.weightlosstracker.data.local.mappers.QuoteLocalMapper
import com.example.weightlosstracker.data.remote.QuoteNetworkMapper
import com.example.weightlosstracker.data.remote.QuotesRetrofitApi
import com.example.weightlosstracker.domain.Quote
import com.example.weightlosstracker.util.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DefaultQuotesRepository constructor(
    private val quotesRetrofitApi: QuotesRetrofitApi,
    private val quoteDao: QuoteDao,
    private val quoteNetworkMapper: QuoteNetworkMapper,
    private val quoteLocalMapper: QuoteLocalMapper
) : QuotesRepository {

    override suspend fun getQuote(): Flow<DataState<Quote>> = flow {
        emit(DataState.Loading)
        try {
            val response = quotesRetrofitApi.getQuoteOfDay()
            if (response.isSuccessful) {
                response.body()?.let {
                    val quote = quoteNetworkMapper.mapFromEntity(it.first())
                    quoteDao.insertQuote(quoteLocalMapper.mapToEntity(quote))
                    val quotes = quoteDao.getAllQuotes()
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