package com.mayank.expensetracker.internal.di

import androidx.lifecycle.ViewModel
import com.mayank.expensetracker.core.ViewModelKey
import com.mayank.expensetracker.ui.dashboard.DashboardViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelFactoryModule::class])
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(value = DashboardViewModel::class)
    abstract fun provideDashboardViewModel(vm: DashboardViewModel): ViewModel
}