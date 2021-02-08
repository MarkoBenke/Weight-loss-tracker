package com.example.weightlosstracker.di

import com.example.weightlosstracker.data.local.SettingsManager
import com.example.weightlosstracker.data.local.dao.QuoteDAO
import com.example.weightlosstracker.data.local.dao.UserDAO
import com.example.weightlosstracker.data.local.dao.WeightEntryDAO
import com.example.weightlosstracker.data.local.mappers.QuoteLocalMapper
import com.example.weightlosstracker.data.local.mappers.UserMapper
import com.example.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.example.weightlosstracker.data.remote.QuoteNetworkMapper
import com.example.weightlosstracker.data.remote.QuotesApi
import com.example.weightlosstracker.repository.qoutes.DefaultQuotesRepository
import com.example.weightlosstracker.repository.qoutes.QuotesRepository
import com.example.weightlosstracker.repository.user.DefaultUserRepository
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.repository.weightentry.DefaultWeightEntryRepository
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//TODO maybe this should view model component and scope
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideQuotesRepository(
        quotesApi: QuotesApi, quoteDAO: QuoteDAO,
        networkMapper: QuoteNetworkMapper, localMapper: QuoteLocalMapper
    ): QuotesRepository = DefaultQuotesRepository(quotesApi, quoteDAO, networkMapper, localMapper)

    @Singleton
    @Provides
    fun provideUserRepository(
        userDAO: UserDAO,
        weightEntryDAO: WeightEntryDAO,
        settingsManager: SettingsManager,
        userMapper: UserMapper
    ): UserRepository = DefaultUserRepository(
        userDAO,
        weightEntryDAO,
        settingsManager,
        userMapper
    )

    @Singleton
    @Provides
    fun provideWeightEntryRepository(
        weightEntryDAO: WeightEntryDAO, userDAO: UserDAO, mapper: WeightEntryMapper
    ): WeightEntryRepository = DefaultWeightEntryRepository(weightEntryDAO, userDAO, mapper)
}