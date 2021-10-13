package com.mayank.expensetracker.internal.di

import com.mayank.expensetracker.internal.repo.TransactionRepo
import com.mayank.expensetracker.internal.usecase.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetTransactionUseCase(repo: TransactionRepo): GetTransactionsUseCase {
        return GetTransferUseCaseImpl(repo)
    }

    @Singleton
    @Provides
    fun provideAddTransactionUseCase(repo: TransactionRepo): AddTransactionUseCase {
        return AddTransactionUseCaseImpl(repo)
    }

    @Singleton
    @Provides
    fun provideDeleteTransactionUseCase(repo: TransactionRepo): DeleteTransactionsUseCase {
        return DeleteTransactionsUseCaseImpl(repo)
    }
}