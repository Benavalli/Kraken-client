package com.example.krakenclient.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class WeatherWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}
