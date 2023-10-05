package com.testtask.polytech.domain.usecases

import com.mylibrary.core.interactor.UseCase
import com.mylibrary.core.type.Either
import com.mylibrary.core.type.Failure
import com.mylibrary.core.type.None
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.domain.repository.LocalRepository
import com.testtask.polytech.domain.repository.RemoteRepository

class GetCategoriesListUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository,
): UseCase<List<CategoryModel>, None>() {

    override suspend fun run(params: None): Either<Failure, List<CategoryModel>> {
        // 1. get actual list categories
        val response = remoteRepository.getCategoriesList()

        // 2. if response failure, try receive from local storage
        if (response.isLeft) {
            val list = localRepository.getCacheCategoriesList()

            // return local cache list
            if (list.isNotEmpty()) {
                return Either.Right(list)
            }

        } else {
            // save cache if we get newest list
            val list = (response as Either.Right)
            localRepository.saveCacheCategoriesList(list.b)
        }

        return response
    }

}