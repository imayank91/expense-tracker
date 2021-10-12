package com.mayank.expensetracker.internal.utils

import java.time.LocalDate
import java.util.*

fun LocalDate.formatDate() =
    "${
        month.name.lowercase(Locale.getDefault())
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    } $dayOfMonth, $year "
