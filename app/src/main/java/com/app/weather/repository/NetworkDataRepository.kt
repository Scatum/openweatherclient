package com.app.weather.repository

import android.util.Log
import com.app.weather.BuildConfig
import com.app.weather.convertor.Converter
import com.app.weather.emitter.ErrorType
import com.app.weather.emitter.RemoteErrorEmitter
import com.app.weather.service.AppRetrofitService
import com.app.weather.model.entity.WeatherEntity
import com.app.weather.service.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkDataRepository(
    private val appRetrofitService: AppRetrofitService,
    private val localDataRepository: LocalDataRepository
) : RemoteErrorEmitter {
    companion object {
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }


    /***
     * the return value
     * **null if network request is failed or local data is empty
     * **Updated data
     * */
    suspend fun getUpdatedForecastsWeatherData():List<WeatherEntity>? {
        val response = safeApiCall(this@NetworkDataRepository) {
            appRetrofitService.fetchCurrentAndForecastsWeatherData(40.177200,44.503490,
                BuildConfig.EXCLUDE,BuildConfig.METRIC, BuildConfig.API_KAY)
        }
        if (response != null && response.isSuccessful && response.body() != null) {
            val weatherEntities = Converter.weatherDTO2Entity(response.body()!!)
            localDataRepository.saveAllData(weatherEntities)
            return weatherEntities
        }
        return null
    }


    suspend inline fun <T> safeApiCall(
        emitter: RemoteErrorEmitter,
        crossinline responseFunction: suspend () -> T
    ): T? {
        return try {
            val response = withContext(Dispatchers.IO) { responseFunction.invoke() }
            response
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.printStackTrace()
                Log.e("ApiSafeCalls", " Safe Call error: ${e.localizedMessage}", e.cause)
                when (e) {
                    is HttpException -> {
                        val body = e.response()?.errorBody()
                        emitter.onError(getErrorMessage(body))
                    }
                    is SocketTimeoutException -> emitter.onError(ErrorType.TIMEOUT)
                    is IOException -> emitter.onError(ErrorType.NETWORK)
                    else -> emitter.onError(ErrorType.UNKNOWN)
                }
            }
            null
        }
    }

    override fun onError(msg: String) {
        Log.e("ERROR!!!!", msg)
    }

    override fun onError(errorType: ErrorType) {
        Log.e("ERROR!!!!", errorType.name)
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }


}