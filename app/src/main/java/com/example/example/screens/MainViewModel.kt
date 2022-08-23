package com.example.example.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.data.model.Article
import com.example.example.domain.usecase.GetNewsUseCase
import com.example.example.util.Error
import com.example.example.util.ViewModelContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

interface MainViewModel : ViewModelContract {

    val isLoading: Flow<Boolean>
    val newsList: Flow<List<Article>>
    val error: Flow<Error>

    class Default @Inject constructor(
        private val getNewsUseCase: GetNewsUseCase
    ) : MainViewModel, ViewModel() {

        private val mutex = Mutex()

        private val loading = MutableStateFlow(0)

        override val isLoading = loading.asStateFlow().map { it != 0 }
        override val newsList = MutableStateFlow(emptyList<Article>())
        override val error = MutableSharedFlow<Error>()

        init {
            viewModelScope.launch {
                launchDataLoad {
                    Log.i("aza", "init")
                    getNewsUseCase.execute()
                        .doOnSuccess { newsList.emit(it.articles) }
                        .doOnFailure { error.emit(it) }
                }
            }
        }

        private fun launchDataLoad(block: suspend () -> Unit) = viewModelScope.launch {
            loading.increment()
            block()
            loading.decrement()
        }

        private suspend fun MutableStateFlow<Int>.increment() {
            mutex.withLock { emit(value + 1) }
        }

        private suspend fun MutableStateFlow<Int>.decrement() {
            mutex.withLock { emit(value - 1) }
        }
    }
}