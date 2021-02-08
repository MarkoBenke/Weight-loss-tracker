package com.example.weightlosstracker.di

import android.content.Context
import androidx.room.Room
import com.example.weightlosstracker.data.local.WeightLossDatabase
import com.example.weightlosstracker.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, WeightLossDatabase::class.java, Constants.DATABASE_NAME)
            .build()

    @Singleton
    @Provides
    fun provideUserDao(database: WeightLossDatabase) = database.userDao()

    @Singleton
    @Provides
    fun provideQuoteDao(database: WeightLossDatabase) = database.quoteDao()

    @Singleton
    @Provides
    fun provideWeightEntryDao(database: WeightLossDatabase) = database.weightEntryDao()
}