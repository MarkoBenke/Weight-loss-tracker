package com.marko.weightlosstracker.di

import com.google.firebase.auth.FirebaseAuth
import com.marko.weightlosstracker.data.network.FirebaseHelper
import com.marko.weightlosstracker.data.network.services.user.FirebaseUserService
import com.marko.weightlosstracker.data.network.services.QuotesService
import com.marko.weightlosstracker.data.network.services.user.UserService
import com.marko.weightlosstracker.data.network.services.weightentry.FirebaseWeightEntryService
import com.marko.weightlosstracker.data.network.services.auth.AuthService
import com.marko.weightlosstracker.data.network.services.auth.FirebaseAuthService
import com.marko.weightlosstracker.data.network.services.weightentry.WeightEntryService
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

    @Provides
    @Singleton
    fun provideUserService(firebaseHelper: FirebaseHelper): UserService =
        FirebaseUserService(firebaseHelper)

    @Provides
    @Singleton
    fun provideWeightEntryService(firebaseHelper: FirebaseHelper): WeightEntryService =
        FirebaseWeightEntryService(firebaseHelper)

    @Singleton
    @Provides
    fun provideAuthService(firebaseAuth: FirebaseAuth): AuthService =
        FirebaseAuthService(firebaseAuth)
}