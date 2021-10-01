package com.example.krakenclient.network

import com.example.krakenclient.model.RelayResponse
import com.example.krakenclient.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST

interface KrakenServerEndpoint {

    @GET("temperature-humidity")
    fun getServerWeather(): Single<WeatherResponse>

    @GET("relays-state")
    fun getRelaysState(): Single<List<RelayResponse>>

    @POST("pump-relay-state")
    fun postPumpRelayState(): Single<RelayResponse>

    @POST("light-relay-state")
    fun postLightRelayState(): Single<RelayResponse>

    @POST("humidifier-relay-state")
    fun postHumidifierRelayState(): Single<RelayResponse>

    @POST("exhaust-relay-state")
    fun postExhaustRelayState(): Single<RelayResponse>
}
