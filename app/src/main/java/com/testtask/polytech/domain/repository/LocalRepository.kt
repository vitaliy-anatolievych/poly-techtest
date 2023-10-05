package com.testtask.polytech.domain.repository

import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel

interface LocalRepository {

    fun saveCacheCategoriesList(listCategoryModel: List<CategoryModel>)
    fun getCacheCategoriesList(): List<CategoryModel>

    fun saveCacheCategoriesDetailsList(key: String, listCategoryModel: List<BookModel>)
    fun getCacheCategoriesDetailsList(key: String): List<BookModel>
}