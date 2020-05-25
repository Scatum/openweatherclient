package com.app.weather.repository

import com.app.weather.db.AppDatabase
import com.app.weather.model.entity.WeatherEntity
import com.app.weather.service.AppSettingsService
import com.app.weather.service.NotificationService
import org.koin.core.KoinComponent

class LocalDataRepository(
    private val notificationService: NotificationService,
    private val appDatabase: AppDatabase,
    private val appSettingsService: AppSettingsService

) : KoinComponent {

    suspend fun saveAllData(weatherEntity: List<WeatherEntity>): Unit {

        val insertedInfo = appDatabase.weatherDao().insertAll(weatherEntity.toTypedArray())
        if (!appSettingsService.foreground()) {
            for (item in weatherEntity) {
                notificationService.createNotification(
                    item
                )
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
}