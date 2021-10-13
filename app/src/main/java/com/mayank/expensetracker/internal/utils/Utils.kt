package com.mayank.expensetracker.internal.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import kotlin.math.abs

fun LocalDate.formatDate() =
    "${
        month.name.lowercase(Locale.getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } $dayOfMonth, $year "


fun parseDouble(value: String?): Double {
    return if (value == null || value.isEmpty()) Double.NaN else "%.2f".format(value.toDouble()).toDouble()
}

@SuppressLint("SimpleDateFormat")
fun Date.toDateString(): String {
    return SimpleDateFormat("dd MMMM yyyy").format(this)
}

fun Double.toDollars(): String {
    val prefix = if (this < 0) "-$" else "$"
    return "$prefix${"%.2f".format(abs(this)).toDouble()}"
}

fun String.isCredit(): Boolean = equals(TransactionType.Income.name, true)

enum class TransactionType {
    Expense,
    Income
}