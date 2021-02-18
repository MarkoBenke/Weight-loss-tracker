package com.example.weightlosstracker.di

import com.example.weightlosstracker.repository.FakeQuotesRepositoryAndroidTest
import com.example.weightlosstracker.repository.FakeUserRepositoryAndroidTest
import com.example.weightlosstracker.repository.FakeWeightEntryRepositoryAndroidTest
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

    var shouldReturnError: Boolean = false

    @Singleton
    @Provides
    fun provideErrorBoolean(): Boolean {
        return shouldReturnError
    }

    @Singleton
    @Provides
    fun provideQuotesRepository(): QuotesRepository = FakeQuotesRepositoryAndroidTest()

    @Singleton
    @Provides
    fun provideUserRepository(shouldReturnError: Boolean): UserRepository =
        FakeUserRepositoryAndroidTest(shouldReturnError)

    @Singleton
    @Provides
    fun provideWeightEntryRepository(): WeightEntryRepository = FakeWeightEntryRepositoryAndroidTest()
}