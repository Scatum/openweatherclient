package com.app.weather.service

class AppSettingsService {
    private var appForeground = false
    fun foreground() = appForeground
    fun foreground(foreground: Boolean) {
        appForeground = foreground
    }

}