package com.testtask.polytech.di

import android.content.Context
import com.mylibrary.core.network.NetworkHandler
import com.mylibrary.core.network.Request
import com.mylibrary.core.network.ServiceFactory
import com.testtask.polytech.BuildConfig
import com.testtask.polytech.data.repositories.local.LocalRepositoryImpl
import com.testtask.polytech.data.repositories.remote.RemoteRepositoryImpl
import com.testtask.polytech.data.repositories.remote.service.ApiService
import com.testtask.polytech.domain.repository.LocalRepository
import com.testtask.polytech.domain.repository.RemoteRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<LocalRepository> {
        LocalRepositoryImpl(
            preferences = get()
        )
    }

    single<RemoteRepository> {
        RemoteRepositoryImpl(
            request = get(),
            service = get(),
        )
    }

    single {
        Request(networkHandler = get())
    }

    single {
        NetworkHandler(context = androidApplication())
    }

    single<ApiService> {
        ServiceFactory.makeService(true, ApiService::class.java, BuildConfig.API_ENDPOINT)
    }

    single {
        androidContext().getSharedPreferences(LocalRepositoryImpl.MAIN_STORAGE_KEY, Context.MODE_PRIVATE)
    }

}