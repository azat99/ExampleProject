package com.example.example.di

import com.example.example.data.repository.RepositoryImpl
import com.example.example.domain.repository.Repository
import dagger.Binds
import dagger.Module

@Module
interface DataBindModule {

    @Binds
    fun bindRepository(repository: RepositoryImpl): Repository
}