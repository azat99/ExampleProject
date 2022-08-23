package com.example.example.di

import androidx.lifecycle.ViewModel
import com.example.example.screens.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelBindModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.Default::class)
    fun bindAccountsViewModel(viewModel: MainViewModel.Default): ViewModel
}