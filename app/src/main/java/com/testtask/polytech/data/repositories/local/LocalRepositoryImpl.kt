package com.testtask.polytech.data.repositories.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.domain.repository.LocalRepository
import java.lang.reflect.Type
import kotlin.reflect.KClass


class LocalRepositoryImpl(private val preferences: SharedPreferences) : LocalRepository {

    override fun saveCacheCategoriesList(listCategoryModel: List<CategoryModel>) {
        preferences.edit()
            .putString(CACHE_CATEGORIES, Gson().toJson(listCategoryModel))
            .apply()
    }

    override fun getCacheCategoriesList(): List<CategoryModel> {
        val json = preferences.getString(CACHE_CATEGORIES, null)
        if (!json.isNullOrBlank()) {
            return parseArray(json = json)
        }
        return emptyList()
    }

    override fun saveCacheCategoriesDetailsList(key: String,listCategoryModel: List<BookModel>) {
        preferences.edit()
            .putString(key, Gson().toJson(listCategoryModel))
            .apply()
    }

    override fun getCacheCategoriesDetailsList(key: String): List<BookModel> {
        val json = preferences.getString(key, null)
        if (!json.isNullOrBlank()) {
            return parseArray(json = json)
        }

        return emptyList()
    }

    private inline fun <reified T> parseArray(json: String): List<T> {
        val gson = GsonBuilder().create()
        val typeToken = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(json, typeToken) as List<T>
    }

    companion object {
        private const val CACHE_CATEGORIES = "cache_categories"
        const val MAIN_STORAGE_KEY = "MAIN_STORAGE"
    }
}