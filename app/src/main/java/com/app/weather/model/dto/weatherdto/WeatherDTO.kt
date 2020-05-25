package com.app.weather.model.dto.weatherdto

data class WeatherDTO(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Long
)