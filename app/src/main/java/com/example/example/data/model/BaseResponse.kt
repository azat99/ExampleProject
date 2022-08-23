package com.example.example.data.model

import androidx.annotation.Keep
import com.example.example.util.Error
import com.example.example.util.Result
import com.example.example.util.toFailure
import com.example.example.util.toSuccess
import com.google.gson.annotations.SerializedName

@Keep
open class BaseResponse {

    @SerializedName("status")
    val success: String? = null

    @SerializedName("reason")
    val reason: String? = null
}

fun BaseResponse.toResult() = toResult {}

inline fun <T : BaseResponse, R> T.toResult(onSuccess: T.() -> R) = if (success == "ok") {
    onSuccess().toSuccess()
} else {
    reason?.let(String::toFailure) ?: Result.Failure(Error.Predefined.Server)
}