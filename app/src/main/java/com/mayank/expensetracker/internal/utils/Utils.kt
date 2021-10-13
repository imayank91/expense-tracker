package com.mayank.expensetracker.internal.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun LocalDate.formatDate() =
    "${
        month.name.lowercase(Locale.getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } $dayOfMonth, $year "


fun parseDouble(value: String?): Double {
    return if (value == null || value.isEmpty()) Double.NaN else value.toDouble()
}

@SuppressLint("SimpleDateFormat")
fun Date.toDateString(): String {
    return SimpleDateFormat("dd MMMM yyyy").format(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toMonth(): String {
    return SimpleDateFormat("MMMM").format(this)
}

fun String.isCredit() : Boolean = equals(TransactionType.Income.name, true)

enum class TransactionType{
    Expense,
    Income
}