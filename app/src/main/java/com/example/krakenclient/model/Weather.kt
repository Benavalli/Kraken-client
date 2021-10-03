package com.example.krakenclient.model

import com.google.gson.annotations.SerializedName

data class CityWeatherResponse(val main: WeatherResponse)

data class GrowWeatherResponse(@SerializedName("temperature-humidity") val main: WeatherResponse)

data class WeatherResponse(val temp: Double, val humidity: Double)

data class DeviceWeatherResponse(val temp: Float, val pressure: Float)
