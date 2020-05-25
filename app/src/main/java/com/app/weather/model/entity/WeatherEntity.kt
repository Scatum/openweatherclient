package com.app.weather.model.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 ***Note: This App database does not have a foreign key.
 *  this is because there is no need to create it.
 *  I prefer having an API that allows me to create many
 * tables so that I can build a different architecture for the database and repositories
 *
 * */
@Entity
data class WeatherEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lon") val lon: Double,
    @ColumnInfo(name = "timezone") val timezone: String,
    @ColumnInfo(name = "timezone_offset") val timezoneOffset: Long,
    @ColumnInfo(name = "info_type") val infoType: String,
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "sunrise") val sunrise: String,
    @ColumnInfo(name = "sunset") val sunset: String,
    @ColumnInfo(name = "temp_day") val tempDay: Double,
    @ColumnInfo(name = "temp_min") val tempMin: Double,
    @ColumnInfo(name = "temp_max") val tempMax: Double,
    @ColumnInfo(name = "temp_night") val tempNight: Double,
    @ColumnInfo(name = "temp_evening") val tempEvening: Double,
    @ColumnInfo(name = "temp_morning") val tempMorning: Double,
    @ColumnInfo(name = "pressure") val pressure: Long,
    @ColumnInfo(name = "humidity") val humidity: Long,
    @ColumnInfo(name = "dew_point") val dewPoint: Double,
    @ColumnInfo(name = "wind_speed") val windSpeed: Double,
    @ColumnInfo(name = "wind_deg") val windDeg: Long,
    @ColumnInfo(name = "clouds") val clouds: Long,
    @ColumnInfo(name = "rain") val rain: Double,
    @ColumnInfo(name = "uvi") val uvi: Double,
    @Embedded val description: Description?

)