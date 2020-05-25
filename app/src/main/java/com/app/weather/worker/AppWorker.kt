package com.app.weather.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.weather.repository.NetworkDataRepository
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception


class AppWorker(
     private val mContext: Context,
     private val workerParams: WorkerParameters
) :
    Worker(mContext, workerParams), KoinComponent {
    private val viewModelJob = SupervisorJob()
    val uiScope = CoroutineScope(Dispatchers.IO + viewModelJob)


    private val networkDataRepository: NetworkDataRepository by inject()
    override fun doWork(): Result {
        try {

            Log.e("doWork", "start")
            uiScope.launch {
                networkDataRepository.getUpdatedForecastsWeatherData()
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