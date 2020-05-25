package com.app.weather.worker

import android.content.Context
import androidx.annotation.NonNull
import androidx.work.*
import com.app.weather.service.PreferenceService
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.TimeUnit


class StartWorker(
    @NonNull context: Context,
    @NonNull workerParams: WorkerParameters
) :
    Worker(context, workerParams), KoinComponent {
    private val preferenceService: PreferenceService by inject()
    @NonNull
    override fun doWork(): Result {
        val constraints: Constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequest.Builder(AppWorker::class.java)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                preferenceService.getOptionType(),
                TimeUnit.MINUTES
            )
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance().enqueue(workRequest)
        return Result.retry()
    }
}