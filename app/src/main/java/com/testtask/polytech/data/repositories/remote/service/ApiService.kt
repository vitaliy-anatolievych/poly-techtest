package com.testtask.polytech.data.repositories.remote.service

import com.testtask.polytech.BuildConfig
import com.testtask.polytech.data.repositories.remote.dto.BooksResponse
import com.testtask.polytech.data.repositories.remote.dto.CategoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        // api version
        private const val VERSION = "v3"

        // methods
        private const val LIST_CATEGORIES = "$VERSION/lists/names.json"
        private const val LIST_BOOKS = "$VERSION/lists/current"

        // params
        private const val TOKEN = "api-key"
    }

    @GET(LIST_CATEGORIES)
    fun getCategories(
        @Query(TOKEN) token: String = BuildConfig.TOKEN,
    ): Call<CategoriesResponse>

    @GET("$LIST_BOOKS/{list}.json")
    fun getBooks(
        @Path("list") list: String,
        @Query(TOKEN) token: String = BuildConfig.TOKEN,
    ): Call<BooksResponse>
}