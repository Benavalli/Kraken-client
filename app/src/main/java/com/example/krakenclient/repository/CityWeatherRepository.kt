package com.example.krakenclient.repository

import android.content.SharedPreferences
import com.example.krakenclient.data.CityWeatherDto
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface CityWeatherRepository {

    fun getCityWeather(): Single<CityWeatherDto>
    fun saveCity(city: String): Completable
    fun getCity(): Single<String>
    fun clearCityWeatherPrefs(): Completable
}

class CityWeatherRepositoryImp(
    private val endpoint: CityWeatherEndpoint,
    private val sharedPreferences: SharedPreferences,
) : CityWeatherRepository {

    companion object {
        const val PREFS_WEATHER_CITY = "CityWeatherRepositoryImp.PREFS_WEATHER_CITY"
        private const val PREFS_WEATHER_CITY_KEY = "CityWeatherRepositoryImp.PREFS_WEATHER_CITY_KEY"
        private const val PREFS_WEATHER_DEFAULT_CITY = "sunnyvale"
    }

    private lateinit var city: String

    override fun getCityWeather(): Single<CityWeatherDto> {
        return getCity().flatMap {
            city = it
            endpoint.getCurrentCityWeather(it)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { CityWeatherDto(city, it.weather) }
    }

    override fun saveCity(city: String) = Completable.fromAction {
        sharedPreferences.edit().putString(PREFS_WEATHER_CITY_KEY, city).apply()
    }

    override fun getCity(): Single<String> {
        return Single.create {
            it.onSuccess(
                sharedPreferences.getString(
                    PREFS_WEATHER_CITY_KEY,
                    PREFS_WEATHER_DEFAULT_CITY
                ).orEmpty()
            )
        }
    }

    override fun clearCityWeatherPrefs() = Completable.fromAction {
        sharedPreferences.edit().remove(PREFS_WEATHER_CITY_KEY).apply()
    }
}
