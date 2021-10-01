package com.example.krakenclient.model

import kotlin.math.roundToInt

data class CityWeatherResponse(val main: WeatherResponse)

data class WeatherResponse(val temp: Double, val humidity: Double) {
    val temperature: Int = temp.roundToInt()
    val roundedHumidity: Int = humidity.roundToInt()
}

data class DeviceWeatherResponse(val temp: Float, val pressure: Float) {
    val temperature: Int = temp.roundToInt()
    val roundedPressure: Int = pressure.roundToInt()
}
