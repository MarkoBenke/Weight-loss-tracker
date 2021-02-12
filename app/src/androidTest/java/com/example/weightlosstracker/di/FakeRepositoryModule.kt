package com.example.weightlosstracker.di

import com.example.weightlosstracker.repository.FakeQuotesRepository
import com.example.weightlosstracker.repository.FakeUserRepositoryAndroidTest
import com.example.weightlosstracker.repository.FakeWeightEntryRepository
import com.example.weightlosstracker.repository.quotes.QuotesRepository
import com.example.weightlosstracker.repository.user.UserRepository
import com.example.weightlosstracker.repository.weightentry.WeightEntryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class]
)
object FakeRepositoryModule {

    @Singleton
    @Provides
    fun provideQuotesRepository(): QuotesRepository = FakeQuotesRepository()

    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository = FakeUserRepositoryAndroidTest()

    @Singleton
    @Provides
    fun provideWeightEntryRepository(): WeightEntryRepository = FakeWeightEntryRepository()
}