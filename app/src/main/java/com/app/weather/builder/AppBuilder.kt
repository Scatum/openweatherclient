package com.app.weather.builder

import android.content.ComponentName
import android.content.Intent
import com.app.weather.BuildConfig

object AppBuilder {
    fun getBaseUrl() = BuildConfig.HTTP_HTTPS + BuildConfig.BASE_URL + BuildConfig.API_VERSION

    fun buildWifiScreenIntent(): Intent {
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val cn =
            ComponentName("com.android.settings", "com.android.settings.wifi.WifiSettings")
        intent.component = cn
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return intent
    }
}