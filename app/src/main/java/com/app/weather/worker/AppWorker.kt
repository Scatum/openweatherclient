package com.app.weather.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.weather.repository.NetworkDataRepository
import com.app.weather.service.NetworkService
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class AppWorker(
    private val mContext: Context,
    private val workerParams: WorkerParameters
) :
    Worker(mContext, workerParams), KoinComponent {
    private val viewModelJob = SupervisorJob()
    val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    private val networkDataRepository: NetworkDataRepository by inject()
    private val networkService: NetworkService by inject()
    override fun doWork(): Result {
        try {

            Log.e("doWork", "start")
            uiScope.launch {
                if (networkService.isNetworkAvailable()) {
                    networkDataRepository.getUpdatedForecastsWeatherData()
                }
            }
            return Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("doWork", "IOException ${e.message}")

        }
        uiScope.cancel()
        return Result.retry()
    }

}