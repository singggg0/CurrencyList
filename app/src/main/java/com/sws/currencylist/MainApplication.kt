package com.sws.currencylist

import android.app.Application
import com.sws.currencylist.di.currencyModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(currencyModule)
        }
    }
}