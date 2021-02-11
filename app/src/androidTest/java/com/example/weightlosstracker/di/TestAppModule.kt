package com.example.weightlosstracker.di

import android.content.Context
import androidx.room.Room
import com.example.weightlosstracker.data.local.WeightLossDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {

    @Named("test_db")
    @Provides
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, WeightLossDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}