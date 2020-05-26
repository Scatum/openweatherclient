package com.app.weather.repository

import com.app.weather.constant.AppTheme
import com.app.weather.db.AppDatabase
import com.app.weather.model.entity.WeatherEntity
import com.app.weather.service.AppSettingsService
import com.app.weather.service.NotificationService
import com.app.weather.service.PreferenceService
import org.koin.core.KoinComponent

class LocalDataRepository(
    private val notificationService: NotificationService,
    private val appDatabase: AppDatabase,
    private val appSettingsService: AppSettingsService,
    private val preferenceService: PreferenceService

) : KoinComponent {

    suspend fun saveAllData(weatherEntity: List<WeatherEntity>): Unit {

        appDatabase.weatherDao().insertAll(weatherEntity.toTypedArray())
        if (!appSettingsService.foreground()) {
            for (item in weatherEntity) {
                notificationService.createNotification(item)
            }
        }

        return Unit
    }

    suspend fun getWeatherDataList() = appDatabase.weatherDao().getAll()


    suspend fun getWeatherInfoById(id: Int): WeatherEntity? {
        return try {
            appDatabase.weatherDao().findByWeatherId(id)
        } catch (ex: Exception) {
            null
        }
    }

    suspend fun getUpdaterOption() = preferenceService.getOptionType()

    /**@return Returns true if the new values were successfully written*/
    suspend fun setUpdaterOption(minute: Long): Boolean? {
        return preferenceService.saveOptionType(minute)
    }

    suspend fun getInterfaceMode(): AppTheme {
        return preferenceService.getInterfaceMode()
    }

    suspend fun setInterfaceMode(appTheme: AppTheme): Boolean {
        return preferenceService.saveInterfaceMode(appTheme)
    }
}