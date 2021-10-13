package com.mayank.expensetracker.ui.dashboard

import com.mayank.expensetracker.ui.components.ItemType

internal data class DashboardScreenState(
    val transactions: List<TransactionState>,
)

internal sealed class TransactionState

internal data class HeaderTransactionState(
    val title: String,
) : TransactionState()

internal data class BalanceTransactionState(
    val description: String,
    val amount: String,
    val isCredit: Boolean,
    var itemType: ItemType = ItemType.DEFAULT,
) : TransactionState()