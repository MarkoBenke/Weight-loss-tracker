package com.example.weightlosstracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weightlosstracker.data.local.dao.QuoteDAO
import com.example.weightlosstracker.data.local.dao.UserDAO
import com.example.weightlosstracker.data.local.dao.WeightEntryDAO
import com.example.weightlosstracker.data.local.model.QuoteCache
import com.example.weightlosstracker.data.local.model.UserCache
import com.example.weightlosstracker.data.local.model.WeightEntryCache

@Database(entities = [QuoteCache::class, UserCache::class, WeightEntryCache::class], version = 1)
abstract class WeightLossDatabase: RoomDatabase() {

    abstract fun quoteDao(): QuoteDAO
    abstract fun userDao(): UserDAO
    abstract fun weightEntryDao(): WeightEntryDAO
}