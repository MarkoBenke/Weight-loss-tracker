package com.marko.weightlosstracker.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marko.weightlosstracker.data.local.entities.QuoteCache

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteCache)

    @Query("SELECT * FROM quotes_table")
    suspend fun getAllQuotes(): List<QuoteCache>
}