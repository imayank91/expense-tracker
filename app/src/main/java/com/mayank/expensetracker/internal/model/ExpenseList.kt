package com.mayank.expensetracker.internal.model

internal data class ExpenseData(val desc: String, val amount: String)

internal val fakelist by lazy {
    listOf(ExpenseData("Coffee from starbucks", "-$7"),
        ExpenseData("Grocery", "-$7"),
        ExpenseData("Salary", "-$7"),
        ExpenseData("Food take out", "-$7")
    )
}