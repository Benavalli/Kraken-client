package com.example.krakenclient.repository

import com.example.krakenclient.data.RelayStateRequest
import com.example.krakenclient.data.RelaysResponse
import com.example.krakenclient.data.WeatherResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface KrakenServerRepository {

    fun getGrowWeather(): Single<WeatherResponse>

    fun getRelays(): Single<RelaysResponse>

    fun changeLightRelayState(state: RelayStateRequest): Completable

    fun changePumpRelayState(state: RelayStateRequest): Completable

    fun changeHumidifierRelayState(state: RelayStateRequest): Completable

    fun changeExhaustRelayState(state: RelayStateRequest): Completable
}

class KrakenServerRepositoryImp(
    private val endpoint: KrakenServerEndpoint
) : KrakenServerRepository {

    override fun getGrowWeather(): Single<WeatherResponse> {
        return endpoint.getServerWeather()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.weather }
    }

    override fun getRelays(): Single<RelaysResponse> {
        return endpoint.getRelays()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeLightRelayState(state: RelayStateRequest): Completable {
        return endpoint.postLightRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changePumpRelayState(state: RelayStateRequest): Completable {
        return endpoint.postPumpRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeHumidifierRelayState(state: RelayStateRequest): Completable {
        return endpoint.postHumidifierRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun changeExhaustRelayState(state: RelayStateRequest): Completable {
        return endpoint.postExhaustRelayState(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
