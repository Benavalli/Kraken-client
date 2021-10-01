package com.example.krakenclient.network

import com.example.krakenclient.model.WeatherResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface KrakenServerRepository {

    fun getGrowWeather(): Single<WeatherResponse>
}

class KrakenServerRepositoryImp(private val endpoint: KrakenServerEndpoint) : KrakenServerRepository {

    override fun getGrowWeather(): Single<WeatherResponse> {
        return endpoint.getServerWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
