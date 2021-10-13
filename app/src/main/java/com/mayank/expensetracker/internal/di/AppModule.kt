package com.mayank.expensetracker.internal.di

import com.mayank.expensetracker.MainActivity
import com.mayank.expensetracker.internal.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(application: MainActivity): AppDatabase {
        return AppDatabase.invoke(application.applicationContext)
    }
}
