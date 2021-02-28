package com.marko.weightlosstracker.di

import com.marko.weightlosstracker.data.local.SettingsManager
import com.marko.weightlosstracker.data.local.dao.QuoteDao
import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.mappers.QuoteLocalMapper
import com.marko.weightlosstracker.data.local.mappers.UserMapper
import com.marko.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.marko.weightlosstracker.data.remote.QuoteNetworkMapper
import com.marko.weightlosstracker.data.remote.QuotesService
import com.marko.weightlosstracker.repository.quotes.DefaultQuotesRepository
import com.marko.weightlosstracker.repository.quotes.QuotesRepository
import com.marko.weightlosstracker.repository.user.DefaultUserRepository
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.repository.weightentry.DefaultWeightEntryRepository
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideQuotesRepository(
        quotesService: QuotesService, quoteDao: QuoteDao,
        networkMapper: QuoteNetworkMapper, localMapper: QuoteLocalMapper
    ): QuotesRepository =
        DefaultQuotesRepository(quotesService, quoteDao, networkMapper, localMapper)

    @ViewModelScoped
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

    @ViewModelScoped
    @Provides
    fun provideWeightEntryRepository(
        weightEntryDao: WeightEntryDao, userDao: UserDao, mapper: WeightEntryMapper
    ): WeightEntryRepository = DefaultWeightEntryRepository(weightEntryDao, userDao, mapper)
}