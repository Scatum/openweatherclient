package com.app.weather.model.entity

import androidx.room.ColumnInfo

data class Description(
    @ColumnInfo(name = "weather_id")
    val weatherId: Int,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "weather_main")
    val weatcherMain: String,//Rain, etc


    @ColumnInfo(name = "weather_icon")
    val weatherIcon: String// http://openweathermap.org/img/wn/10d@2x.png


)