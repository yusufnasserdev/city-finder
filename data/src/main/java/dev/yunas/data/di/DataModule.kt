package dev.yunas.data.di

import dev.yunas.data.client.NetworkClient
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
}