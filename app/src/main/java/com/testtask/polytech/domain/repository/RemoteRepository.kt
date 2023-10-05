package com.testtask.polytech.domain.repository

import com.mylibrary.core.type.Either
import com.mylibrary.core.type.Failure
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel

interface RemoteRepository {

    fun getCategoriesList(): Either<Failure, List<CategoryModel>>
    fun getCategoriesDetailsList(name: String): Either<Failure, List<BookModel>>
}