package com.mayank.expensetracker.internal.di

import androidx.lifecycle.ViewModel
import com.mayank.expensetracker.core.ViewModelKey
import com.mayank.expensetracker.ui.dashboard.DashboardViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
internal class ViewModelModule {

    @Provides
    @IntoMap
    @ViewModelKey(value = DashboardViewModel::class)
    fun provideDashboardViewModel():ViewModel {
        return DashboardViewModel()
    }
}