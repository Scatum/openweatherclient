package com.app.weather.model.dto.weatherdto

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)