package com.example.krakenclient.network

import com.example.krakenclient.model.Relay
import com.example.krakenclient.model.RelaysResponse
import com.example.krakenclient.model.WeatherResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface KrakenServerRepository {

    fun getGrowWeather(): Single<WeatherResponse>

    fun getRelays(): Single<RelaysResponse>

    fun changeLightRelayState(state: String): Single<Relay>

    fun changePumpRelayState(state: String): Single<Relay>

    fun changeHumidifierRelayState(state: String): Single<Relay>

    fun changeExhaustRelayState(state: String): Single<Relay>
}

class KrakenServerRepositoryImp(private val endpoint: KrakenServerEndpoint) : KrakenServerRepository {

    override fun getGrowWeather(): Single<WeatherResponse> {
        return endpoint.getServerWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.main }
    }

    override fun getRelays(): Single<RelaysResponse> {
        return endpoint.getRelays()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeLightRelayState(state: String): Single<Relay> {
        return endpoint.postLightRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changePumpRelayState(state: String): Single<Relay> {
        return endpoint.postPumpRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeHumidifierRelayState(state: String): Single<Relay> {
        return endpoint.postHumidifierRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeExhaustRelayState(state: String): Single<Relay> {
        return endpoint.postExhaustRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
