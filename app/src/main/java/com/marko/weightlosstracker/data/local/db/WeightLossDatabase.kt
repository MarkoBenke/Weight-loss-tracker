package com.marko.weightlosstracker.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marko.weightlosstracker.data.local.dao.QuoteDao
import com.marko.weightlosstracker.data.local.dao.UserDao
import com.marko.weightlosstracker.data.local.dao.WeightEntryDao
import com.marko.weightlosstracker.data.local.model.QuoteCache
import com.marko.weightlosstracker.data.local.model.UserCache
import com.marko.weightlosstracker.data.local.model.WeightEntryCache

@Database(
    entities = [QuoteCache::class, UserCache::class, WeightEntryCache::class],
    version = 1,
    exportSchema = false
)
abstract class WeightLossDatabase : RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
    abstract fun userDao(): UserDao
    abstract fun weightEntryDao(): WeightEntryDao
}