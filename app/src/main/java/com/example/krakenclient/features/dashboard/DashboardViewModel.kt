package com.example.krakenclient.features.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krakenclient.model.DeviceWeatherResponse
import com.example.krakenclient.model.WeatherResponse
import com.example.krakenclient.network.CityWeatherRepository
import com.example.krakenclient.network.KrakenServerRepository
import com.example.krakenclient.things.RainbowHatManager
import io.reactivex.disposables.CompositeDisposable

class DashboardViewModel(
    private val cityWeatherRepository: CityWeatherRepository,
    private val krakenServerRepository: KrakenServerRepository,
    private val rainbowHatManager: RainbowHatManager
) : ViewModel() {

    companion object {
        private const val TAG = "DashboardViewModel"
    }

    private val subscription = CompositeDisposable()
    val cityWeather: MutableLiveData<WeatherResponse> by lazy { MutableLiveData<WeatherResponse>() }
    val growWeather: MutableLiveData<WeatherResponse> by lazy { MutableLiveData<WeatherResponse>() }
    val deviceWeather: MutableLiveData<DeviceWeatherResponse> by lazy { MutableLiveData<DeviceWeatherResponse>() }

    fun getCityWeather() {
        subscription.add(
            cityWeatherRepository.getCityWeather("sunnyvale").subscribe({
                cityWeather.value = it
            }, { throwable -> Log.e(TAG, throwable.message ?: "") })
        )
    }

    fun getGrowWeather() {
        subscription.add(
            krakenServerRepository.getGrowWeather().subscribe({
                growWeather.value = it
            }, { throwable -> Log.e(TAG, throwable.message ?: "") })
        )
    }

    fun getDeviceWeather() {
        deviceWeather.value = rainbowHatManager.getTemperatureHumidity()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}
