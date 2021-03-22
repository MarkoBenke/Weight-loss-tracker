package com.marko.weightlosstracker.di

import com.google.firebase.auth.FirebaseAuth
import com.marko.weightlosstracker.data.local.dao.QuoteDao
import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.mappers.QuoteMapper
import com.marko.weightlosstracker.data.local.mappers.UserMapper
import com.marko.weightlosstracker.data.local.mappers.WeightEntryMapper
import com.marko.weightlosstracker.data.local.settings.SettingsManager
import com.marko.weightlosstracker.data.remote.datasource.QuotesService
import com.marko.weightlosstracker.data.remote.datasource.UserService
import com.marko.weightlosstracker.data.remote.datasource.WeightEntryService
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
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
//TODO Singleton or ViewModel scope
object RepositoryModule {

    @ViewModelScoped
    @Provides
    fun provideQuotesRepository(
        quotesService: QuotesService, quoteDao: QuoteDao, mapper: QuoteMapper
    ): QuotesRepository =
        DefaultQuotesRepository(quotesService, quoteDao, mapper)

    @ViewModelScoped
    @Provides
    fun provideUserRepository(
        userDao: UserDao,
        userService: UserService,
        weightEntryDao: WeightEntryDao,
        weightEntryService: WeightEntryService,
        settingsManager: SettingsManager,
        userMapper: UserMapper,
        weightEntryMapper: WeightEntryMapper
    ): UserRepository = DefaultUserRepository(
        userDao,
        userService,
        weightEntryDao,
        weightEntryService,
        settingsManager,
        userMapper,
        weightEntryMapper
    )

    @ViewModelScoped
    @Provides
    fun provideWeightEntryRepository(
        weightEntryDao: WeightEntryDao,
        weightEntryService: WeightEntryService,
        userDao: UserDao,
        userService: UserService,
        entryMapper: WeightEntryMapper
    ): WeightEntryRepository =
        DefaultWeightEntryRepository(
            weightEntryDao,
            weightEntryService,
            userDao,
            userService,
            entryMapper
        )

    @ViewModelScoped
    @Provides
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository =
        DefaultAuthRepository(firebaseAuth)
}