package com.example.krakenclient.application

import android.app.Application
import com.example.krakenclient.di.appModule
import com.example.krakenclient.di.networkModule
import com.example.krakenclient.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class KrakenApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@KrakenApplication)
            modules(listOf(appModule, networkModule, repositoryModule))
        }
    }
}
