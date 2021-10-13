package com.mayank.expensetracker.internal.di

import com.mayank.expensetracker.ui.dashboard.TransactionsUIStateMapper
import com.mayank.expensetracker.ui.dashboard.TransactionsUIStateMapperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class ToolsModule {

    @Singleton
    @Provides
    fun provideTransactionUIStateMapper(): TransactionsUIStateMapper {
        return TransactionsUIStateMapperImpl()
    }
}