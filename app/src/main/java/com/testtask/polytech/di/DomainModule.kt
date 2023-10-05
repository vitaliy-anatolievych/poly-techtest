package com.testtask.polytech.di

import com.testtask.polytech.domain.usecases.GetCategoriesDetailsUseCase
import com.testtask.polytech.domain.usecases.GetCategoriesListUseCase
import org.koin.dsl.module

val domainModule = module {

    single {
        GetCategoriesListUseCase(
            localRepository = get(),
            remoteRepository = get(),
        )
    }

    single {
        GetCategoriesDetailsUseCase(
            localRepository = get(),
            remoteRepository = get(),
        )
    }

}