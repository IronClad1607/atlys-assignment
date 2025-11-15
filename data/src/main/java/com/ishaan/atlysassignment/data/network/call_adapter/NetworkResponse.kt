package com.ishaan.atlysassignment.data.network.call_adapter

import com.google.gson.annotations.SerializedName

sealed class NetworkResponse<out T : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T>()

    /**
     * Error response with body
     */
    data class Error(val body: ErrorResponse) : NetworkResponse<Nothing>()
}

data class ErrorResponse(
    @SerializedName("error_message")
    val errorMessage: String? = null,
    val code: Int? = null,
    val success: Boolean = false
) {
    fun error() = errorMessage ?: "Unknown error occurred"
}