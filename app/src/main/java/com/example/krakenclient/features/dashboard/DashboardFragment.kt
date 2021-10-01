package com.example.krakenclient.features.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.krakenclient.R
import com.example.krakenclient.databinding.FragmentDashboardBinding
import com.example.krakenclient.model.DeviceWeatherResponse
import com.example.krakenclient.model.WeatherResponse
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the PeripheralManager
 * For example, the snippet below will open a GPIO pin and set it to HIGH:
 *
 * val manager = PeripheralManager.getInstance()
 * val gpio = manager.openGpio("BCM6").apply {
 *     setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * }
 * gpio.value = true
 *
 * You can find additional examples on GitHub: https://github.com/androidthings
 */
class DashboardFragment : Fragment() {

    private val viewModel by viewModel<DashboardViewModel>()
    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityWeatherObserver = Observer<WeatherResponse> { cityWeather ->
            updateCityWeather(cityWeather.temperature, cityWeather.roundedHumidity)
        }

        val deviceWeatherObserver = Observer<DeviceWeatherResponse> { deviceWeather ->
            updateDeviceWeather(deviceWeather.temperature, deviceWeather.roundedPressure)
        }

        val growWeatherObserver = Observer<WeatherResponse> { growWeather ->
            updateGrowWeather(growWeather.temperature, growWeather.roundedHumidity)
        }

        viewModel.cityWeather.observe(viewLifecycleOwner, cityWeatherObserver)
        viewModel.growWeather.observe(viewLifecycleOwner, growWeatherObserver)
        viewModel.deviceWeather.observe(viewLifecycleOwner, deviceWeatherObserver)

        viewModel.getCityWeather()
        viewModel.getGrowWeather()
        viewModel.getDeviceWeather()
    }

    private fun updateCityWeather(temperature: Int, humidity: Int) {
        binding.cityTitle.text = "Sunnyvale"
        binding.cityTemperatureValue.text = temperature.toString()
        binding.cityHumidityValue.text = humidity.toString()
    }

    private fun updateDeviceWeather(temperature: Int, pressure: Int) {
        binding.homeTemperatureValue.text = temperature.toString()
        binding.homePressureValue.text = pressure.toString()
    }

    private fun updateGrowWeather(temperature: Int, humidity: Int) {
        binding.growTemperatureValue.text = temperature.toString()
        binding.growHumidityValue.text = humidity.toString()
    }
}
