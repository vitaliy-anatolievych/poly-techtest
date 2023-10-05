package com.testtask.polytech.domain.usecases

import com.mylibrary.core.interactor.UseCase
import com.mylibrary.core.type.Either
import com.mylibrary.core.type.Failure
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.domain.repository.LocalRepository
import com.testtask.polytech.domain.repository.RemoteRepository

class GetCategoriesDetailsUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
) :
    UseCase<List<BookModel>, GetCategoriesDetailsUseCase.Params>() {

    data class Params(val categoryModel: CategoryModel)

    override suspend fun run(params: Params): Either<Failure, List<BookModel>> {

        // 1. get actual list books for category
        val response =
            remoteRepository.getCategoriesDetailsList(params.categoryModel.displayNameEncoded)

        // 2. if response failure, try receive from local storage
        if (response.isLeft) {
            val list =
                localRepository.getCacheCategoriesDetailsList(params.categoryModel.displayNameEncoded)

            // return local cache list
            if (list.isNotEmpty()) {
                return Either.Right(list)
            }

        } else {
            // save cache if we get newest list
            val list = (response as Either.Right)
            localRepository.saveCacheCategoriesDetailsList(
                params.categoryModel.displayNameEncoded,
                list.b,
            )
        }

        return response

    }
}