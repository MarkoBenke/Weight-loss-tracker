package com.example.weightlosstracker.di

import com.example.weightlosstracker.data.remote.QuotesRetrofitApi
import com.example.weightlosstracker.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideQuotesApi(): QuotesRetrofitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
            .build()
            .create(QuotesRetrofitApi::class.java)
    }
}