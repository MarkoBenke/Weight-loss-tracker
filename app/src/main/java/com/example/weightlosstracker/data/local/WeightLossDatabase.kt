package com.example.weightlosstracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weightlosstracker.data.local.dao.QuoteDao
import com.example.weightlosstracker.data.local.dao.UserDao
import com.example.weightlosstracker.data.local.dao.WeightEntryDao
import com.example.weightlosstracker.data.local.model.QuoteCache
import com.example.weightlosstracker.data.local.model.UserCache
import com.example.weightlosstracker.data.local.model.WeightEntryCache

@Database(entities = [QuoteCache::class, UserCache::class, WeightEntryCache::class], version = 1)
abstract class WeightLossDatabase: RoomDatabase() {

    abstract fun quoteDao(): QuoteDao
    abstract fun userDao(): UserDao
    abstract fun weightEntryDao(): WeightEntryDao
}