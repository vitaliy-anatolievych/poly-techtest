package com.testtask.polytech.data.repositories.remote

import com.mylibrary.core.network.Request
import com.mylibrary.core.type.Either
import com.mylibrary.core.type.Failure
import com.testtask.polytech.data.repositories.remote.mappers.CategoriesMapper
import com.testtask.polytech.data.repositories.remote.service.ApiService
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.domain.repository.RemoteRepository

class RemoteRepositoryImpl(
    private val service: ApiService,
    private val request: Request
) : RemoteRepository {

    override fun getCategoriesList(): Either<Failure, List<CategoryModel>> {
        return request.make(service.getCategories()) {
            CategoriesMapper.mapListCategoriesDTOToCategoryModel(it.results)
        }
    }

    override fun getCategoriesDetailsList(name: String): Either<Failure, List<BookModel>> {
        return request.make(service.getBooks(list = name)) {
            CategoriesMapper.mapListBooksDTOToBookModel(it.results)
        }
    }
}