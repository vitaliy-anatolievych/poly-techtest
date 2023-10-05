package com.mylibrary.core.network

import com.google.gson.Gson
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {

    private var _URL: String? = null
    private val URL: String
        get() = _URL ?: throw NullPointerException("ServiceFactory: URL is undefined")

    fun <S>makeService(isDebug: Boolean, api: Class<S>, url: String): S {
        _URL = url
        val okHttpClient = makeOkHttpClient(
            makeLoggingInterceptor((isDebug))
        )
        return makeService(okHttpClient, Gson(), api)
    }

    private fun <S>makeService(okHttpClient: OkHttpClient, gson: Gson, api: Class<S>): S {
        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit.create(api)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(0, 5, TimeUnit.MINUTES))
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}