package com.mayank.expensetracker.internal.repo

import com.mayank.expensetracker.internal.data.local.AppDatabase
import com.mayank.expensetracker.internal.model.Transaction
import javax.inject.Inject

internal class TransactionRepo @Inject constructor(private val db: AppDatabase) {

    suspend fun insert(transaction: Transaction) = db.getTransactionDao().insertTransaction(
        transaction
    )

    fun getAllTransactions() = db.getTransactionDao().getAllTransactions()

    suspend fun deleteByID(id: Int) = db.getTransactionDao().deleteTransactionByID(id)
}
