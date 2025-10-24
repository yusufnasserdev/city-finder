package dev.yunas.data.client

import dev.yunas.data.BuildConfig
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent

@Single
class NetworkClient : KoinComponent {

    @Single
    fun provideHttpClient(): HttpClient = configureBaseSettings()

    private fun configureBaseSettings(): HttpClient {
        return HttpClient {
            defaultRequest {
                url(BASE_URL)
                contentType(ContentType.Application.Json)
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        explicitNulls = false
                    })
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message = "Http client: $message", tag = "HttpClient")
                    }
                }
            }

            install(plugin = Auth) {
                headers {
                    append("x-rapidapi-key", BuildConfig.API_KEY)
                    append("x-rapidapi-host", BASE_URL)
                }
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 20_000
                connectTimeoutMillis = 20_000
                socketTimeoutMillis = 20_000
            }
        }
    }

    private companion object {
        const val BASE_URL = "wft-geo-db.p.rapidapi.com"
    }
}