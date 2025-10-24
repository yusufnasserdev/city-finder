package dev.yunas.cityfinder

import android.app.Application
import dev.yunas.cityfinder.di.AppModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class CityFinderApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Napier.base(DebugAntilog())

        startKoin {
            androidLogger()
            androidContext(this@CityFinderApp)
            modules(AppModule().module)
        }
    }
}