package com.example.krakenclient.network

import com.example.krakenclient.model.WeatherResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface CityWeatherRepository {

    fun getCityWeather(city: String): Single<WeatherResponse>
}

class CityWeatherRepositoryImp(private val endpoint: CityWeatherEndpoint) : CityWeatherRepository {

    override fun getCityWeather(city: String): Single<WeatherResponse> {
        return endpoint.getCurrentCityWeather(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.main }
    }
}
