package com.example.example.di

import com.example.example.domain.usecase.GetNewsUseCase
import dagger.Binds
import dagger.Module

@Module
interface DomainBindModule {

    @Binds
    fun bindUseCase(useCase: GetNewsUseCase.Default): GetNewsUseCase
}