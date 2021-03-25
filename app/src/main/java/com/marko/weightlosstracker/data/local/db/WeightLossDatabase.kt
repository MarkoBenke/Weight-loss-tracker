package com.marko.weightlosstracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marko.weightlosstracker.data.local.dao.QuoteDao
import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.entities.QuoteEntity
import com.marko.weightlosstracker.data.local.entities.UserEntity
import com.marko.weightlosstracker.data.local.entities.WeightEntryEntity

@Database(
    entities = [QuoteEntity::class, UserEntity::class, WeightEntryEntity::class],
    version = 2,
    exportSchema = false
)
abstract class WeightLossDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
    abstract fun userDao(): UserDao
    abstract fun weightEntryDao(): WeightEntryDao
}