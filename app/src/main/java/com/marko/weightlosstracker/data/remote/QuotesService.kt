package com.marko.weightlosstracker.data.remote

import com.marko.weightlosstracker.data.remote.response.QuoteResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface QuotesService {

    @Headers("Accept: application/json")
    @GET("api/quotes/random?limit=1&category=motivational")
    suspend fun fetchQuote(): Response<ArrayList<QuoteResponse>>
}