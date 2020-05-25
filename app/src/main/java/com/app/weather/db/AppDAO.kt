package com.app.weather.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.weather.model.entity.WeatherEntity

@Dao
interface AppDAO {
    @Query("SELECT * FROM WeatherEntity")
    suspend fun getAll(): List<WeatherEntity>

    @Query("SELECT * FROM weatherentity WHERE weather_id = :id")
    suspend   fun findByWeatherId(id: Int): WeatherEntity

    /**
     *
     * the return value
     * if long value is -1 then item is not replaced
     * if long value is weatherEntity id then we sure that weatherEntity has
     * just inserted
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherEntity: Array<WeatherEntity>): List<Long>

}