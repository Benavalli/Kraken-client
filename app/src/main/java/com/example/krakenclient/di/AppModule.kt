package com.example.krakenclient.di

import com.example.krakenclient.features.dashboard.DashboardViewModel
import com.example.krakenclient.things.RainbowHatManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<Gson> { GsonBuilder().setLenient().create() }
    single { RainbowHatManager() }
    viewModel { DashboardViewModel(get(), get(), get()) }
    //viewModel { SwitchesViewModel(get()) }
}
