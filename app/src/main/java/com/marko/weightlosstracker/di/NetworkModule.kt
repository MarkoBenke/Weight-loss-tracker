package com.marko.weightlosstracker.di

import com.marko.weightlosstracker.data.remote.QuotesService
import com.marko.weightlosstracker.util.Constants.BASE_URL
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
    fun provideQuotesApi(): QuotesService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL)
            .build()
            .create(QuotesService::class.java)
    }
}