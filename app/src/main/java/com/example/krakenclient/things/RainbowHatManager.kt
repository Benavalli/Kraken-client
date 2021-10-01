package com.example.krakenclient.things

import com.example.krakenclient.model.DeviceWeatherResponse
import com.example.krakenclient.model.WeatherResponse
import com.google.android.things.contrib.driver.bmx280.Bmx280
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat

class RainbowHatManager {

    fun getTemperatureHumidity(): DeviceWeatherResponse {
        val sensor = RainbowHat.openSensor()
        sensor.temperatureOversampling = Bmx280.OVERSAMPLING_1X
        sensor.pressureOversampling = Bmx280.OVERSAMPLING_1X
        sensor.hasHumiditySensor()
        val deviceWeatherResponse = DeviceWeatherResponse(sensor.readTemperature(),
            sensor.readPressure())
        sensor.close()
        return deviceWeatherResponse
    }
}
