package com.marko.weightlosstracker.data.remote.model

import androidx.annotation.Keep

@Keep class QuoteResponse(
    val id: Int,
    val author: String,
    val category: String,
    val quote: String
)
