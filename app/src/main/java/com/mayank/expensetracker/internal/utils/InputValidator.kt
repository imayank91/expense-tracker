package com.mayank.expensetracker.internal.utils

object InputValidator {
    fun isValid(vararg input: String):Boolean = input.any { it.isBlank() }.not()
}
