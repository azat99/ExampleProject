package com.example.example.util

import android.content.Context
import android.view.LayoutInflater
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.viewbinding.ViewBinding
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

inline fun Lifecycle.doOnCreated(crossinline block: () -> Unit) {
    if (currentState.isAtLeast(Lifecycle.State.CREATED)) {
        block()
    } else {
        coroutineScope.launchWhenCreated { block() }
    }
}

@MainThread
inline fun <T : ViewBinding> ComponentActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
): Lazy<T> {
    val lazyBinding = lazy(LazyThreadSafetyMode.NONE) { bindingInflater.invoke(layoutInflater) }
    lifecycle.doOnCreated { setContentView(lazyBinding.value.root) }
    return lazyBinding
}

fun Error.extractString(): String {
    return when (this) {
        is Error.Unknown -> text
        is Error.Predefined.Timeout -> "Превышено время ожидания ответа от сервера. Пожалуйста, повторите попытку позже"
        is Error.Predefined.NoNetwork -> "Не удалось подключиться к серверу. Пожалуйста, проверьте соединение с интернетом"
        is Error.Predefined.Server -> "Произошла ошибка на сервере. Пожалуйста, повторите попытку позже"
        is Error.Predefined.Illegal -> "Произошла ошибка во время выполнения запроса, пожалуйста, повторите попытку"
    }
}

@MainThread
suspend fun Context.alertPayload(
    title: String? = null,
    message: String? = null,
    positiveText: String = "Ok",
    negativeText: String? = "Cancel",
) = suspendCoroutine<AlertAnswer> { continuation ->
    val builder = AlertDialog.Builder(this)
    title?.let(builder::setTitle)
    message?.let(builder::setMessage)
    negativeText?.let {
        builder.setNegativeButton(negativeText) { _, _ -> continuation.resume(AlertAnswer.Cancel) }
    }
    builder
        .setPositiveButton(positiveText) { _, _ -> continuation.resume(AlertAnswer.Ok) }
        .setOnCancelListener { continuation.resume(AlertAnswer.Cancel) }
        .show()
}

sealed class AlertAnswer {
    object Ok : AlertAnswer()
    object Cancel : AlertAnswer()
}