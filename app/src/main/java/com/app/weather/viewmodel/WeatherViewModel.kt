package com.app.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.app.weather.constant.DataType
import com.app.weather.constant.LiveDataResponseStatus
import com.app.weather.model.entity.WeatherEntity
import com.app.weather.repository.LocalDataRepository
import com.app.weather.repository.NetworkDataRepository
import com.app.weather.service.NetworkService

class WeatherViewModel(
    private val networkDataRepository: NetworkDataRepository,
    private val localDataRepository: LocalDataRepository,
    private val networkService: NetworkService
) : ViewModel() {

    /**
     *
     * @param publishStoredData first of all try to get data from local db
     * @param updateLocalData try to update local data
     * will get data from local db then will make network request
     * to update local data, after updating will publish updated data to requared screen
     *
     * */
    fun getForecastsWeatherData(
        publishStoredData: Boolean = true,
        updateLocalData: Boolean = true
    ): LiveData<Triple<DataType, LiveDataResponseStatus, List<WeatherEntity>?>> = liveData {

        suspend fun publishStoredData() {
            if (publishStoredData) {
                emit(
                    Triple(
                        DataType.OLD,
                        LiveDataResponseStatus.SUCCESS,
                        localDataRepository.getWeatherDataList()
                    )
                )
            }
        }

        suspend fun publishUpdatedData() {
            if (networkService.isNetworkAvailable()) {
                val data = networkDataRepository.getUpdatedForecastsWeatherData()
                val resStatus = if (data.isNullOrEmpty()) LiveDataResponseStatus.UNKNOUN_ERROR
                else LiveDataResponseStatus.SUCCESS
                emit(
                    Triple(
                        DataType.UPDATED,
                        resStatus,
                        data
                    )
                )

            } else {
                emit(
                    Triple(
                        DataType.UPDATED,
                        LiveDataResponseStatus.NO_INTERNET_CONNECTION,
                        null
                    )
                )
            }
        }

        //  viewModelScope.launch {// we shouldn't close viewModelScope
        publishStoredData()
        publishUpdatedData()
        // }

        // return returnValue
    }

    override fun onCleared() {
        super.onCleared()
    }

}