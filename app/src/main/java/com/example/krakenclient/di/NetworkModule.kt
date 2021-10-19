package com.example.krakenclient.di

import com.example.krakenclient.BuildConfig
import com.example.krakenclient.repository.CityWeatherEndpoint
import com.example.krakenclient.repository.KrakenServerEndpoint
import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { providesCityWeatherApi(get()) }
    single { providesKrakenServerApi(get()) }
}

fun providesCityWeatherApi(gson: Gson): CityWeatherEndpoint {
    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.CITY_WEATHER_BASE_URL)
        .client(providesWeatherCityOkHttpClient())
        .build()
    return retrofit.create(CityWeatherEndpoint::class.java)
}

fun providesKrakenServerApi(gson: Gson): KrakenServerEndpoint {
    val retrofit = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.KRAKEN_SERVER_BASE_URL)
        .client(providesKrakenServerOkHttpClient())
        .build()
    return retrofit.create(KrakenServerEndpoint::class.java)
}

private fun providesKrakenServerOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
}

private fun providesWeatherCityOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor { chain ->
            var request = chain.request()
            val url = addWeatherCityDefaultQueryParameters(request)
            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }.build()
}

private fun addWeatherCityDefaultQueryParameters(request: Request): HttpUrl {
    return request.url.newBuilder()
        .addQueryParameter("appid", BuildConfig.API_KEY)
        .addQueryParameter("units", "metric")
        .build()
}
