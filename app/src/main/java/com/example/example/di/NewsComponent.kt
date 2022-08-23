package com.example.example.di

import android.app.Application
import com.example.example.screens.MainPage
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DataBindModule::class,
        DomainBindModule::class,
        ViewModelBindModule::class,
        ViewModelFactoryModule::class
    ]
)
interface NewsComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): NewsComponent
    }

    fun inject(page: MainPage)
}