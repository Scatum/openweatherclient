package com.app.weather.application

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.app.weather.service.AppSettingsService
import com.app.weather.service.WorkerService

import org.koin.core.KoinComponent
import org.koin.core.inject


class AppStateListener : LifecycleObserver, KoinComponent {
    private val appSettingsService: AppSettingsService by inject()
    private val workerService: WorkerService by inject()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        workerService.setup()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private fun startSomething() {
        appSettingsService.foreground(true)
        workerService.cancel()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stopSomething() {
        appSettingsService.foreground(false)
        workerService.start()

    }
}