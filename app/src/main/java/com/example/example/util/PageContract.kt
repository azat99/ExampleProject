package com.example.example.util

import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.emitAll

interface PageContract<VB : ViewBinding, VM : ViewModelContract> : PageScopeHolder {

    val binding: VB

    val vm: VM

    @CallSuper
    fun initialize() = Unit

    fun <T> Flow<T>.collectInScope(action: suspend (value: T) -> Unit) =
        pageScope.launchWhenStarted { collect(action) }

    fun <T> Flow<T>.collectInScope(collector: FlowCollector<T>) =
        pageScope.launchWhenStarted { collector.emitAll(this@collectInScope) }
}