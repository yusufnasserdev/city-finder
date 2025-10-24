package dev.yunas.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import dev.yunas.data.client.NetworkClient
import dev.yunas.data.service.CitiesApiService
import dev.yunas.data.service.createCitiesApiService
import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("dev.yunas.data")
class DataModule {
    @Single
    fun provideHttpClient(): HttpClient {
        return NetworkClient().provideHttpClient()
    }

    @Single
    fun provideKtorfit(httpClient: HttpClient): Ktorfit {
        return Ktorfit.Builder()
            .httpClient(httpClient)
            .build()
    }

    @Single
    fun provideCitiesApiService(ktorfit: Ktorfit): CitiesApiService {
        return ktorfit.createCitiesApiService()
    }

}