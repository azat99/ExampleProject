package com.example.example.util

sealed class Result<out T> {

    class Success<T>(val data: T) : Result<T>()

    class Failure(val error: Error) : Result<Nothing>()

    inline fun doOnSuccess(onSuccess: (T) -> Unit): Result<T> {
        if (this is Success) {
            onSuccess(data)
        }
        return this
    }

    inline fun doOnFailure(onFailure: (Error) -> Unit): Result<T> {
        if (this is Failure) {
            onFailure(error)
        }
        return this
    }

    inline fun <K> mapSuccess(mapper: (T) -> K): Result<K> {
        return when (this) {
            is Success -> Success(mapper(data))
            is Failure -> this
        }
    }
}

fun <T> T.toSuccess() = Result.Success(this)

fun String.toFailure() = Result.Failure(Error.Unknown(this))