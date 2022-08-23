package com.example.example.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class DaggerViewModelFactory @Inject constructor(
    private val viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = viewModels[modelClass]?.get() ?: throw IllegalArgumentException(
            "Expected ViewModel was not specified in Dagger module or " +
                "module was not included in component"
        )
        return viewModel as T
    }
}