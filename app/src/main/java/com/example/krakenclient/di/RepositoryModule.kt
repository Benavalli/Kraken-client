package com.example.krakenclient.di

import com.example.krakenclient.network.*
import org.koin.dsl.module

val repositoryModule = module {
    single { providesCityWeatherRepository(get()) }
    single { providesKrakenServerRepository(get()) }
}

fun providesCityWeatherRepository(endpoint: CityWeatherEndpoint) : CityWeatherRepository  {
    return CityWeatherRepositoryImp(endpoint)
}

fun providesKrakenServerRepository(endpoint: KrakenServerEndpoint) : KrakenServerRepository {
    return KrakenServerRepositoryImp(endpoint)
}
