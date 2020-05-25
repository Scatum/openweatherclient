package com.app.weather.service

import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.app.weather.worker.StartWorker

class WorkerService {
    private var oneTimeWorkRequest: OneTimeWorkRequest? = null

    fun setup() {
        cancel()// if we need to change periodic parameter from settings
        oneTimeWorkRequest = OneTimeWorkRequest.Builder(StartWorker::class.java).build()
    }

    fun cancel() {
        try {
            WorkManager.getInstance().cancelAllWork()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun start() {
        oneTimeWorkRequest?.let { WorkManager.getInstance().enqueue(it) }
    }
}