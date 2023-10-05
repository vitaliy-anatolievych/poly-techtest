package com.testtask.polytech.app

import android.app.Application
import com.testtask.polytech.di.dataModule
import com.testtask.polytech.di.domainModule
import com.testtask.polytech.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}