package com.example.krakenclient.network

import com.example.krakenclient.model.GrowWeatherResponse
import com.example.krakenclient.model.RelayStateRequest
import com.example.krakenclient.model.RelaysResponse
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface KrakenServerEndpoint {

    @GET("temperature-humidity")
    fun getServerWeather(): Single<GrowWeatherResponse>

    @GET("relays")
    fun getRelays(): Single<RelaysResponse>

    @POST("pump-relay-state")
    fun postPumpRelayState(@Body state: RelayStateRequest): Completable

    @POST("light-relay-state")
    fun postLightRelayState(@Body state: RelayStateRequest): Completable

    @POST("humidifier-relay-state")
    fun postHumidifierRelayState(@Body state: RelayStateRequest): Completable

    @POST("exhaust-relay-state")
    fun postExhaustRelayState(@Body state: RelayStateRequest): Completable
}
