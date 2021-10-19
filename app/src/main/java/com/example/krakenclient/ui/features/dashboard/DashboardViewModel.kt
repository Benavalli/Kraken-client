package com.example.krakenclient.ui.features.dashboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.krakenclient.data.CityWeatherDto
import com.example.krakenclient.data.DeviceWeatherResponse
import com.example.krakenclient.data.WeatherResponse
import com.example.krakenclient.repository.CityWeatherRepository
import com.example.krakenclient.repository.KrakenServerRepository
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
    val cityWeather: MutableLiveData<CityWeatherDto> by lazy { MutableLiveData<CityWeatherDto>() }
    val growWeather: MutableLiveData<WeatherResponse> by lazy { MutableLiveData<WeatherResponse>() }
    val deviceWeather: MutableLiveData<DeviceWeatherResponse> by lazy { MutableLiveData<DeviceWeatherResponse>() }

    fun getCityWeather() {
        subscription.add(
            cityWeatherRepository.getCityWeather().subscribe({
                cityWeather.value = it
            }, { throwable -> Log.e(TAG, throwable.message.orEmpty()) })
        )
    }

    fun getGrowWeather() {
        subscription.add(
            krakenServerRepository.getGrowWeather().subscribe({
                growWeather.value = it
            }, { throwable -> Log.e(TAG, throwable.message.orEmpty()) })
        )
    }

    fun getDeviceWeather() {
        subscription.add(
            rainbowHatManager.getTemperatureHumidity().subscribe({
                deviceWeather.value = it
            }, { throwable -> Log.e(TAG, throwable.message.orEmpty()) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}
