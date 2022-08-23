package com.example.example.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

private const val HTTP_CODE_CLIENT_ERROR = 400
private const val HTTP_CODE_SERVER_ERROR = 500
private const val KEY_ERROR_MESSAGE = "message"

internal suspend inline fun <T> apiCall(
    dispatcher: CoroutineDispatcher,
    request: () -> Result<T>
): Result<T> = try {
    request()
} catch (e: SocketTimeoutException) {
    Result.Failure(Error.Predefined.Timeout)
} catch (e: UnknownHostException) {
    Result.Failure(Error.Predefined.NoNetwork)
} catch (e: HttpException) {
    e.handle(dispatcher)
}

internal suspend fun HttpException.handle(dispatcher: CoroutineDispatcher): Result.Failure {
    val response = response() ?: return Result.Failure(Error.Predefined.Illegal)
    val code = response.code()
    return Result.Failure(
        when {
            code >= HTTP_CODE_SERVER_ERROR -> Error.Predefined.Server
            code >= HTTP_CODE_CLIENT_ERROR -> {
                @Suppress("BlockingMethodInNonBlockingContext")
                (withContext(dispatcher) {
                    val jsonObject = JSONObject(response.errorBody()?.string().orEmpty())
                    if (jsonObject.has(KEY_ERROR_MESSAGE)) {
                        Error.Unknown(jsonObject.getString(KEY_ERROR_MESSAGE))
                    } else {
                        Error.Unknown(message())
                    }
                })
            }
            else -> Error.Predefined.Illegal
        }
    )
}