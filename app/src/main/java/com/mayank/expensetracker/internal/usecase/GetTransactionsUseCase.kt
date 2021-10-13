package com.mayank.expensetracker.internal.usecase

import com.mayank.expensetracker.internal.model.Transaction
import com.mayank.expensetracker.internal.repo.TransactionRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal interface GetTransactionsUseCase {
    suspend fun getTransactions(): Flow<List<Transaction>>
}

internal class GetTransferUseCaseImpl @Inject constructor(private val repo: TransactionRepo) :
    GetTransactionsUseCase {
    override suspend fun getTransactions(): Flow<List<Transaction>> {
        return repo.getAllTransactions()
    }
}