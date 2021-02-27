package com.marko.weightlosstracker.di

import android.content.Context
import androidx.room.Room
import com.marko.weightlosstracker.data.local.WeightLossDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Named

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RoomModule::class]
)
object TestAppModule {

    @Named("test_db")
    @Provides
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, WeightLossDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}