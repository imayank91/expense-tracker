package com.mayank.expensetracker.internal.usecase

import com.mayank.expensetracker.internal.repo.TransactionRepo
import javax.inject.Inject

internal interface DeleteTransactionsUseCase {
    suspend fun deleteTransaction(id: Int)
}

internal class DeleteTransactionsUseCaseImpl @Inject constructor(private val repo: TransactionRepo) :
    DeleteTransactionsUseCase {
    override suspend fun deleteTransaction(id: Int) {
        return repo.deleteByID(id)
    }
}