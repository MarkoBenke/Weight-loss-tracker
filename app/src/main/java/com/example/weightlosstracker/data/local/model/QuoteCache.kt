package com.example.weightlosstracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
data class QuoteCache(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String,
    val category: String,
    val quote: String
)
