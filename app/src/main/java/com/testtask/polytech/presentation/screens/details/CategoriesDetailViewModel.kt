package com.testtask.polytech.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mylibrary.core.viewmodels.BaseViewModel
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.domain.usecases.GetCategoriesDetailsUseCase

class CategoriesDetailViewModel(
    private val getCategoriesDetailsUseCase: GetCategoriesDetailsUseCase,
) : BaseViewModel() {

    private val _getBooksData: MutableLiveData<List<BookModel>> = MutableLiveData()
    val getBooksData: LiveData<List<BookModel>>
        get() = _getBooksData

    fun getListOfBooksFromCategory(categoryModel: CategoryModel) {
        getCategoriesDetailsUseCase(params = GetCategoriesDetailsUseCase.Params(categoryModel)) {
            it.either(::handleFailure, ::handleBooksList)
        }
    }

    private fun handleBooksList(list: List<BookModel>) {
        _getBooksData.value = list
    }

    override fun onCleared() {
        super.onCleared()
        getCategoriesDetailsUseCase.unsubscribe()
    }
}