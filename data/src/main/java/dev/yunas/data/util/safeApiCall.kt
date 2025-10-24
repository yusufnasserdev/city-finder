package dev.yunas.data.util

import de.jensklingenberg.ktorfit.Response
import dev.yunas.domain.exception.CityFinderException
import io.github.aakira.napier.Napier
import io.ktor.http.HttpStatusCode
import kotlinx.io.IOException

suspend inline fun <reified T> safeApiCall(
    apiCall: suspend () -> Response<T>,
): T {
    val response = runCatching { apiCall() }
        .onFailure { e ->
            Napier.d(message = "Network error: ${e.message}")
            throw mapToCityFinderException(e)
        }
        .getOrThrow()

    return when (response.status) {
        HttpStatusCode.OK -> {
            runCatching {
                response.body() ?: run {
                    Napier.d(message = "Response body is null")
                    throw CityFinderException.NoInternetException
                }
            }
                .onFailure { e ->
                    Napier.d(message = "Error parsing response: ${e.message}")
                    throw CityFinderException.NoInternetException
                }
                .getOrThrow()
        }

        HttpStatusCode.Unauthorized -> {
            Napier.d(message = "Unauthorized access")
            throw CityFinderException.UnexpectedException
        }

        HttpStatusCode.TooManyRequests -> {
            Napier.d(message = "Too many requests")
            throw CityFinderException.NoInternetException
        }

        HttpStatusCode.RequestTimeout -> {
            Napier.d(message = "Request timed out")
            throw CityFinderException.NoInternetException
        }

        else -> {
            if (response.status.value in 500..599) {
                Napier.d(message = "Server error: ${response.status.value}")
                throw CityFinderException.NoInternetException
            } else {
                Napier.d(message = "Unknown error: ${response.status.value}")
                throw CityFinderException.UnexpectedException
            }
        }
    }
}

fun mapToCityFinderException(e: Throwable): CityFinderException {
    return when (e) {
        is IOException -> CityFinderException.NoInternetException
        else -> CityFinderException.UnexpectedException
    }
}