package com.marko.weightlosstracker.di

import com.google.firebase.auth.FirebaseAuth
import com.marko.weightlosstracker.data.local.SettingsManager
import com.marko.weightlosstracker.data.local.dao.QuoteDao
import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.mappers.QuoteLocalMapper
import com.marko.weightlosstracker.data.local.mappers.UserMapper
import com.marko.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.marko.weightlosstracker.data.remote.QuoteNetworkMapper
import com.marko.weightlosstracker.data.remote.QuotesService
import com.marko.weightlosstracker.repository.auth.AuthRepository
import com.marko.weightlosstracker.repository.auth.DefaultAuthRepository
import com.marko.weightlosstracker.repository.quotes.DefaultQuotesRepository
import com.marko.weightlosstracker.repository.quotes.QuotesRepository
import com.marko.weightlosstracker.repository.user.DefaultUserRepository
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.repository.weightentry.DefaultWeightEntryRepository
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideQuotesRepository(
        quotesService: QuotesService, quoteDao: QuoteDao,
        networkMapper: QuoteNetworkMapper, localMapper: QuoteLocalMapper
    ): QuotesRepository =
        DefaultQuotesRepository(quotesService, quoteDao, networkMapper, localMapper)

    @Singleton
    @Provides
    fun provideUserRepository(
        userDao: UserDao,
        weightEntryDao: WeightEntryDao,
        settingsManager: SettingsManager,
        userMapper: UserMapper
    ): UserRepository = DefaultUserRepository(
        userDao,
        weightEntryDao,
        settingsManager,
        userMapper
    )

    @Singleton
    @Provides
    fun provideWeightEntryRepository(
        weightEntryDao: WeightEntryDao, userDao: UserDao, mapper: WeightEntryMapper
    ): WeightEntryRepository = DefaultWeightEntryRepository(weightEntryDao, userDao, mapper)

    @Singleton
    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository =
        DefaultAuthRepository(firebaseAuth)

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()
}