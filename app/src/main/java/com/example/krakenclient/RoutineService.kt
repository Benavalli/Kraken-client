package com.example.krakenclient

import android.app.Service
import android.content.Intent
import android.os.*
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import android.util.Log
import com.example.krakenclient.repository.CityWeatherRepository
import com.example.krakenclient.repository.KrakenServerRepository
import com.example.krakenclient.things.RainbowHatManager
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoutineService: Service(), KoinComponent {

    companion object {
        private const val TAG = "RoutineService"
        private const val THREAD_HANDLER = "RoutineService.THREAD_HANDLER_NAME"
    }

    private val cityWeatherRepository by inject<CityWeatherRepository>()
    private val krakenServerRepository by inject<KrakenServerRepository>()
    private val rainbowHatManager by inject<RainbowHatManager>()
    private val subscription = CompositeDisposable()
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {

            subscription.add(

                Single.zip(
                    cityWeatherRepository.getCityWeather(),
                    krakenServerRepository.getGrowWeather(),
                    rainbowHatManager.getTemperatureHumidity(),
                    { cityWeather, growWeather, deviceWeather ->
                        Log.e(TAG, "")

                    }
                ).doOnSuccess {  }
                    .doOnError {
                        Log.e(TAG, it.message.orEmpty())
                    }.doFinally {  }.subscribe()
            )

            // For our sample, we just sleep for 5 seconds.
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        HandlerThread(THREAD_HANDLER, THREAD_PRIORITY_BACKGROUND).apply {
            start()
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
       // Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        // For each start request, send a message to start a job and deliver the
        // start ID so we know which request we're stopping when we finish the job
        serviceHandler?.obtainMessage()?.also { msg ->
            msg.arg1 = startId
            serviceHandler?.sendMessage(msg)
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        subscription.dispose()
    }
}
