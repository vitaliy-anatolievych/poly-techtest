package com.testtask.polytech.presentation.contracts

import androidx.fragment.app.Fragment
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel

fun Fragment.nav(): NavigatorMain? {
    return activity as? NavigatorMain
}

interface NavigatorMain {

    fun goToStartPage()
    fun goToCategoryDetails(category: CategoryModel)
    fun goToBookDetails(url: String)
}