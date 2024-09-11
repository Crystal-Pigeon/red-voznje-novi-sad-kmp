package org.kmp.ktor

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.utils.io.errors.*
import kotlinx.serialization.SerializationException

suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, E> =
    try {
        val response = request { block() }
        ApiResponse.Success(response.body())
    } catch (e: ClientRequestException) {
        ApiResponse.Error.HttpError(e.response.status.value, ErrorBody(e.response.status.value, e.response.status.description))
    } catch (e: ServerResponseException) {
        ApiResponse.Error.HttpError(e.response.status.value, ErrorBody(e.response.status.value, e.response.status.description))
        //ApiResponse.Error.ServerError(ErrorBody(e.response.status.value, e.response.status.description))
    } catch (e: IOException) {
        ApiResponse.Error.NetworkError
    } catch (e: SerializationException) {
        ApiResponse.Error.SerializationError
    }

suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {
        response.body()
    } catch (e: SerializationException) {
        null
    }

sealed class ApiResponse<out T, out E> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<E> : ApiResponse<Nothing, E>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpError<E>(val code: Int, val errorBody: ErrorBody?) : Error<E>()
        //data class ServerError<E>(val body: ErrorBody): Error<E>()
        //message?

        /**
         * Represent IOExceptions and connectivity issues.
         */
        object NetworkError : Error<Nothing>()

        /**
         * Represent SerializationExceptions.
         */
        object SerializationError : Error<Nothing>()
    }
}

data class ErrorBody(
    val statusCode: Int,  // Optional if you handle status codes outside of the body
    val message: String,
    val errorCode: String? = null  // Or any other relevant fields
)