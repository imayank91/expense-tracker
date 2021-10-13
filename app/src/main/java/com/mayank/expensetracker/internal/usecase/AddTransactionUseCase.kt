package com.mayank.expensetracker.internal.usecase

import com.mayank.expensetracker.internal.model.Transaction
import com.mayank.expensetracker.internal.repo.TransactionRepo
import javax.inject.Inject

internal interface AddTransactionUseCase {
    suspend fun addTransaction(transaction: Transaction)
}

internal class AddTransactionUseCaseImpl @Inject constructor(private val repo: TransactionRepo) :
    AddTransactionUseCase {
    override suspend fun addTransaction(transaction: Transaction) {
        repo.insert(transaction)
    }
}