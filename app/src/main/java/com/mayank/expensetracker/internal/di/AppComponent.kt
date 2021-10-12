package com.mayank.expensetracker.internal.di

import com.mayank.expensetracker.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, AndroidInjectionModule::class])
internal abstract class AppComponent : AndroidInjector<MainActivity> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: MainActivity): Builder

        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

    companion object {
        fun initAndInject(activity: MainActivity): AppComponent {
            val component =
                DaggerAppComponent.builder().activity(activity).appModule(AppModule()).build()
            component.inject(activity)
            return component
        }
    }
}