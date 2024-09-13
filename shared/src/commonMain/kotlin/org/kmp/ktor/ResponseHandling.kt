package org.kmp.ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerializationException

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()
    data class Error(val message: String, val cause: Throwable? = null) : ApiResponse<Nothing>()

    fun isSuccess(): Boolean = this is Success<T>
    fun isError(): Boolean = this is Error

    fun getSuccessData(): T? = if (this is Success) this.data else null
    fun getErrorMessage(): String? = if (this is Error) this.message else null
}

suspend fun <T> handleApiResponse(apiCall: suspend () -> T): ApiResponse<T> {
    return try {
        val response = apiCall() // Execute the network call
        ApiResponse.Success(response)
    } catch (e: Throwable) {
        // You can add more error handling logic here (e.g., parsing HTTP errors)
        ApiResponse.Error(e.message ?: "Unknown error", e)
    }
}