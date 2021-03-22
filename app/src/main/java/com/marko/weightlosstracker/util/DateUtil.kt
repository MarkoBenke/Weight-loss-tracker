package com.marko.weightlosstracker.util

import android.text.format.DateUtils
import java.lang.Exception
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "dd.MM.yyyy"

fun getCurrentDate(): String {
    val calendar = Calendar.getInstance()
    val formatter: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return formatter.format(calendar.time)
}

fun parseSelectedDate(date: Date): String {
    val formatter: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return formatter.format(date)
}

fun parseDate(selectedDate: String): Date? {
    val formatter: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return try {
        formatter.parse(selectedDate)
    } catch (e: ParseException) {
        Date()
    }
}

fun parseNoYearDate(selectedDate: String): String {
    return try {
        selectedDate.substring(0, selectedDate.length - 5)
    } catch (e: Exception) {
       ""
    }
}

