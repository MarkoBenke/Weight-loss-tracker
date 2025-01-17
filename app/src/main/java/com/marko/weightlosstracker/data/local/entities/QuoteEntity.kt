package com.marko.weightlosstracker.data.local.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes_table")
@Keep
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String,
    val category: String,
    val quote: String
)
