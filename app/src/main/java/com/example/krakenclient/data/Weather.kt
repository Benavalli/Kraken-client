package com.example.krakenclient.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(val temp: Double, val humidity: Double)

data class CityWeatherResponse(@SerializedName("main") val weather: WeatherResponse)

data class CityWeatherDto(val city: String, val weather: WeatherResponse)

data class GrowWeatherResponse(
    @SerializedName("temperature-humidity") val weather: WeatherResponse
)

data class DeviceWeatherResponse(val temp: Float, val pressure: Float)
