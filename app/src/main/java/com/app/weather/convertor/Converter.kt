package com.app.weather.convertor

import android.annotation.SuppressLint
import com.app.weather.model.dto.weatherdto.Daily
import com.app.weather.model.dto.weatherdto.WeatherDTO
import com.app.weather.model.entity.Description
import com.app.weather.model.entity.WeatherEntity
import java.text.SimpleDateFormat

object Converter {
    fun weatherDTO2Entity(weatherDTO: WeatherDTO): List<WeatherEntity> {

        fun getWeatherEntity(
            daily: Daily,
            lat: Double,
            lon: Double,
            timeZone: String,
            timezoneOffset: Long
        ): WeatherEntity {
            return WeatherEntity(
                daily.dt,
                lat,
                lon,
                timeZone,
                timezoneOffset,
                "daily",
                dt2FormatedDate(daily.dt),
                dt2FormatedDate(daily.sunrise, "HH:MM"),
                dt2FormatedDate(daily.sunset, "HH:MM"),
                daily.temp.day,
                daily.temp.min,
                daily.temp.max,
                daily.temp.night,
                daily.temp.eve,
                daily.temp.morn,
                daily.pressure,
                daily.humidity,
                daily.dew_point,
                daily.wind_speed,
                daily.wind_deg,
                daily.clouds,
                daily.rain,
                daily.uvi,
                Description(
                    daily.weather[0].id,
                    daily.weather[0].description,
                    daily.weather[0].main,
                    iconId2IconUrl(daily.weather[0].icon)
                )
            )
        }

        val list: MutableList<WeatherEntity> = mutableListOf()

        weatherDTO.daily.forEach {
            list.add(
                getWeatherEntity(
                    it,
                    weatherDTO.lat,
                    weatherDTO.lon,
                    weatherDTO.timezone,
                    weatherDTO.timezone_offset
                )
            )
        }
        return list
    }

    private fun iconId2IconUrl(icon: String) =
        String.format("http://openweathermap.org/img/wn/%s@2x.png", icon)

    @SuppressLint("SimpleDateFormat")
    private fun dt2FormatedDate(dt: Long, dataFormat: String = "E, d MMM "): String {
        val date = SimpleDateFormat(dataFormat).format(dt * 1000)
        return date.toString()
    }
}