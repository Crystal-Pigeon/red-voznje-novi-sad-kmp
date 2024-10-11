package org.kmp.ktor

import dev.icerock.moko.resources.desc.StringDesc
import getString
import io.ktor.client.network.sockets.*
import io.ktor.utils.io.errors.*
import org.kmp.experiment.SharedRes

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

sealed class ParsedResponse<out T> {
    data class Success<T>(val data: T) : ParsedResponse<T>()
    data class Error(val message: StringDesc, val cause: Throwable? = null) : ParsedResponse<Nothing>()

    fun isSuccess(): Boolean = this is Success<T>
    fun isError(): Boolean = this is Error

    fun getSuccessData(): T? = if (this is Success) this.data else null
    fun getErrorMessage(): StringDesc? = if (this is Error) this.message else null
}

suspend fun <T> handleParseResponse(parseCall: suspend () -> T): ParsedResponse<T> {
    return try {
        val response = parseCall() // Execute the network call
        ParsedResponse.Success(response)
    } catch (e: IOException) {
        ParsedResponse.Error(getString(SharedRes.strings.error_no_internet_connection))
    } catch (e: Exception) {
        ParsedResponse.Error(getString(SharedRes.strings.error_no_internet_connection))//TODO blame GSP
    }
}