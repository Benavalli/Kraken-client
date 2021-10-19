package com.example.krakenclient.di

import android.content.Context
import android.content.SharedPreferences
import com.example.krakenclient.repository.*
import com.example.krakenclient.repository.CityWeatherRepositoryImp.Companion.PREFS_WEATHER_CITY
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val repositoryModule = module {
    single(named(PREFS_WEATHER_CITY)) { providesCityWeatherSharedPreferences(androidContext()) }
    single { providesCityWeatherRepository(get(), get(named(PREFS_WEATHER_CITY))) }
    single { providesKrakenServerRepository(get()) }
}

fun providesCityWeatherSharedPreferences(context: Context) : SharedPreferences {
    return context.getSharedPreferences(PREFS_WEATHER_CITY, Context.MODE_PRIVATE)
}

fun providesCityWeatherRepository(
    endpoint: CityWeatherEndpoint,
    sharedPreferences: SharedPreferences,
) : CityWeatherRepository {
    return CityWeatherRepositoryImp(endpoint, sharedPreferences)
}

fun providesKrakenServerRepository(endpoint: KrakenServerEndpoint) : KrakenServerRepository {
    return KrakenServerRepositoryImp(endpoint)
}
