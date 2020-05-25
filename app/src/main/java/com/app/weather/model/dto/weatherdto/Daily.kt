package com.app.weather.model.dto.weatherdto

data class Daily(
    val clouds: Long,
    val dew_point: Double,
    val dt: Long,
    val feels_like: Temp,// avoiding create new class,
    // reuse Temp data class to get json with key feels_like
    val humidity: Long,
    val pressure: Long,
    val rain: Double,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val uvi: Double,
    val weather: List<Weather>,
    val wind_deg: Long,
    val wind_speed: Double
)