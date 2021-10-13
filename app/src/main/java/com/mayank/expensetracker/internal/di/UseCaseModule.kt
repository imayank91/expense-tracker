package com.mayank.expensetracker.internal.di

import com.mayank.expensetracker.internal.repo.TransactionRepo
import com.mayank.expensetracker.internal.usecase.AddTransactionUseCase
import com.mayank.expensetracker.internal.usecase.AddTransactionUseCaseImpl
import com.mayank.expensetracker.internal.usecase.GetTransactionsUseCase
import com.mayank.expensetracker.internal.usecase.GetTransferUseCaseImpl
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
}