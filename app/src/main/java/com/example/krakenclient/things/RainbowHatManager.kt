package com.example.krakenclient.things

import com.example.krakenclient.data.DeviceWeatherResponse
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat
import io.reactivex.Single
import java.lang.Exception

class RainbowHatManager {

    fun getTemperatureHumidity(): Single<DeviceWeatherResponse> {
        return try {
            val sensor = RainbowHat.openSensor()
            sensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
            sensor.pressureOversampling = Bmx280.OVERSAMPLING_1X
            sensor.hasHumiditySensor()
            val deviceWeatherResponse = DeviceWeatherResponse(
                sensor.readTemperature(),
                sensor.readPressure()
            )
            sensor.close()
            Single.just(deviceWeatherResponse)
        } catch (exception: Exception) {
            Single.error(exception)
        }
    }
}
