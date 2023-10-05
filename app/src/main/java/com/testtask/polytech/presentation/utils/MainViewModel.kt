package com.testtask.polytech.presentation.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mylibrary.core.type.HandleOnce
import com.mylibrary.core.type.None
import com.mylibrary.core.viewmodels.BaseViewModel
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.domain.usecases.GetCategoriesDetailsUseCase
import com.testtask.polytech.domain.usecases.GetCategoriesListUseCase

class MainViewModel(
    private val getCategoriesListUseCase: GetCategoriesListUseCase,
) : BaseViewModel() {

    private val _getCategoriesData: MutableLiveData<List<CategoryModel>> = MutableLiveData()
    val getCategoriesData: LiveData<List<CategoryModel>>
        get() = _getCategoriesData

    fun getCategoriesList() {
        getCategoriesListUseCase(None()) {
            it.either(::handleFailure, ::handleCategoriesList)
        }
    }

    private fun handleCategoriesList(list: List<CategoryModel>) {
        _getCategoriesData.value = list
    }

    override fun onCleared() {
        super.onCleared()
        getCategoriesListUseCase.unsubscribe()
    }
}