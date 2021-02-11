package com.example.weightlosstracker.di

import com.example.weightlosstracker.data.local.SettingsManager
import com.example.weightlosstracker.data.local.dao.QuoteDao
import com.example.weightlosstracker.data.local.dao.UserDao
import com.example.weightlosstracker.data.local.dao.WeightEntryDao
import com.example.weightlosstracker.data.local.mappers.QuoteLocalMapper
import com.example.weightlosstracker.data.local.mappers.UserMapper
import com.example.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.example.weightlosstracker.data.remote.QuoteNetworkMapper
import com.example.weightlosstracker.data.remote.QuotesRetrofitApi
import com.example.weightlosstracker.repository.quotes.DefaultQuotesRepository
import com.example.weightlosstracker.repository.quotes.QuotesRepository
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
        quotesRetrofitApi: QuotesRetrofitApi, quoteDao: QuoteDao,
        networkMapper: QuoteNetworkMapper, localMapper: QuoteLocalMapper
    ): QuotesRepository = DefaultQuotesRepository(quotesRetrofitApi, quoteDao, networkMapper, localMapper)

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
}