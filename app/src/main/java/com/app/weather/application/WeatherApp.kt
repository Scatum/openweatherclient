package com.app.weather.application

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.app.weather.di.appRepositories
import com.app.weather.di.appServices
import com.app.weather.di.appViewModels
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
        registerProcessLifecycleOwner()
    }

    private fun registerProcessLifecycleOwner() {
        ProcessLifecycleOwner.get()
            .lifecycle
            .addObserver(AppStateListener())
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@WeatherApp)
            androidLogger(Level.DEBUG)
            modules(listOf(appRepositories, appViewModels, appServices))
        }
    }
}