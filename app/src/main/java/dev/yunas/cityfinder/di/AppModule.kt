package dev.yunas.cityfinder.di

import dev.yunas.data.di.DataModule
import dev.yunas.domain.di.DomainModule
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module(includes = [
    DataModule::class,
    DomainModule::class
])
@ComponentScan("dev.yunas.cityfinder")
class AppModule