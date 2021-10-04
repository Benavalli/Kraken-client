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
            updateCityWeather(cityWeather.temp, cityWeather.humidity)
        }

        val deviceWeatherObserver = Observer<DeviceWeatherResponse> { deviceWeather ->
            updateDeviceWeather(deviceWeather.temp, deviceWeather.pressure)
        }

        val growWeatherObserver = Observer<WeatherResponse> { growWeather ->
            updateGrowWeather(growWeather.temp, growWeather.humidity)
        }

        viewModel.cityWeather.observe(viewLifecycleOwner, cityWeatherObserver)
        viewModel.growWeather.observe(viewLifecycleOwner, growWeatherObserver)
        viewModel.deviceWeather.observe(viewLifecycleOwner, deviceWeatherObserver)

        viewModel.getCityWeather()
        viewModel.getGrowWeather()
        viewModel.getDeviceWeather()
    }

    private fun updateCityWeather(temperature: Double, humidity: Double) {
        binding.cityTitle.text = "Sunnyvale"
        binding.cityTemperatureValue.text = getString(R.string.temperature_value_mask, temperature)
        binding.cityHumidityValue.text = getString(R.string.humidity_value_mask, humidity)
    }

    private fun updateDeviceWeather(temperature: Float, pressure: Float) {
        binding.homeTemperatureValue.text = getString(R.string.temperature_value_mask, temperature)
        binding.homePressureValue.text = pressure.toString()
    }

    private fun updateGrowWeather(temperature: Double, humidity: Double) {
        binding.growTemperatureValue.text = getString(R.string.temperature_value_mask, temperature)
        binding.growHumidityValue.text = getString(R.string.humidity_value_mask, humidity)
    }
}
