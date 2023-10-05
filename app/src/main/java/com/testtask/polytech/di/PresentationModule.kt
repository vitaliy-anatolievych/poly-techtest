package com.testtask.polytech.di

import com.testtask.polytech.presentation.screens.details.CategoriesDetailViewModel
import com.testtask.polytech.presentation.utils.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        MainViewModel(
            getCategoriesListUseCase = get(),
        )
    }

    viewModel {
        CategoriesDetailViewModel(
            getCategoriesDetailsUseCase = get(),
        )
    }
}