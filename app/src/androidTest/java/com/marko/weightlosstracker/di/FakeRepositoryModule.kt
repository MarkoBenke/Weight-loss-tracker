package com.marko.weightlosstracker.di

import com.marko.weightlosstracker.repository.FakeQuotesRepositoryAndroidTest
import com.marko.weightlosstracker.repository.FakeUserRepositoryAndroidTest
import com.marko.weightlosstracker.repository.FakeWeightEntryRepositoryAndroidTest
import com.marko.weightlosstracker.repository.quotes.QuotesRepository
import com.marko.weightlosstracker.repository.user.UserRepository
import com.marko.weightlosstracker.repository.weightentry.WeightEntryRepository
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