package com.marko.weightlosstracker.data.network.entities

import androidx.annotation.Keep

@Keep class RemoteQuote(
    val id: Int,
    val author: String,
    val category: String,
    val quote: String
)
