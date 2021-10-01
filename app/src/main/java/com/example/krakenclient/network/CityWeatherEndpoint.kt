package com.example.krakenclient.network

import com.example.krakenclient.model.CityWeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CityWeatherEndpoint {

    @GET("weather")
    fun getCurrentCityWeather(@Query("q") city: String? = "sunnyvale"): Single<CityWeatherResponse>
}
