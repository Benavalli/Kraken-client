package com.example.krakenclient.network

import com.example.krakenclient.model.GrowWeatherResponse
import com.example.krakenclient.model.Relay
import com.example.krakenclient.model.RelaysResponse
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
    fun postPumpRelayState(@Body state: String): Single<Relay>

    @POST("light-relay-state")
    fun postLightRelayState(@Body state: String): Single<Relay>

    @POST("humidifier-relay-state")
    fun postHumidifierRelayState(@Body state: String): Single<Relay>

    @POST("exhaust-relay-state")
    fun postExhaustRelayState(@Body state: String): Single<Relay>
}
