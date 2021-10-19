package com.example.krakenclient.repository

import com.example.krakenclient.data.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CityWeatherEndpoint {

    @GET("weather")
    fun getCurrentCityWeather(@Query("q") city: String): Single<CityWeatherResponse>
}
