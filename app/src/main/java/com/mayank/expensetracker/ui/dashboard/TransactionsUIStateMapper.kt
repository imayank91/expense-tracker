package com.mayank.expensetracker.ui.dashboard

import com.mayank.expensetracker.core.Mapper
import com.mayank.expensetracker.internal.model.Transaction
import com.mayank.expensetracker.internal.utils.isCredit
import com.mayank.expensetracker.internal.utils.toDateString
import com.mayank.expensetracker.ui.components.ItemType
import java.util.*
import javax.inject.Inject

internal interface TransactionsUIStateMapper :
    Mapper<List<Transaction>, List<TransactionState>>

internal class TransactionsUIStateMapperImpl @Inject constructor(
) : TransactionsUIStateMapper {

    override fun map(from: List<Transaction>): List<TransactionState> {
        return arrayListOf<TransactionState>().apply {
            from.groupBy {
                val date = Date(it.timestamp)
                (date.toDateString())
            }.map { (date, transactions) ->
                add(HeaderTransactionState(title = date))
                transactions.forEachIndexed { index, transaction ->
                    add(
                        getBalanceTransactionState(transaction).apply {
                            itemType = ItemType.parse(index, transactions.size)
                        }
                    )
                }
            }
        }
    }

    private fun getBalanceTransactionState(transaction: Transaction) =
        BalanceTransactionState(
            description = transaction.title,
            amount = transaction.amount.toTransactionAmount(transaction.transactionType.isCredit()),
            isCredit = transaction.transactionType.isCredit()
        )

    private fun Double.toTransactionAmount(isCredit: Boolean): String {
        val prefix = if (isCredit) '+' else '-'
        return "$prefix$${toString()}"
    }
}
