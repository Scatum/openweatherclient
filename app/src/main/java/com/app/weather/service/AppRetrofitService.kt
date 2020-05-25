package com.app.weather.service

import com.app.weather.BuildConfig
import com.app.weather.model.dto.weatherdto.WeatherDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AppRetrofitService {

    @GET(BuildConfig.ONCECALL)
    suspend fun fetchCurrentAndForecastsWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") excludes: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Response<WeatherDTO>

    companion object {
        fun create(): AppRetrofitService {
            return RetrofitFactory.makeRetrofitService()
        }
    }
}