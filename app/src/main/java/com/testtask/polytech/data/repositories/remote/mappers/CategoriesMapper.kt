package com.testtask.polytech.data.repositories.remote.mappers

import com.testtask.polytech.data.repositories.remote.dto.CategoriesDTO
import com.testtask.polytech.data.repositories.remote.dto.book.BookResults
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel

object CategoriesMapper {

    fun mapListCategoriesDTOToCategoryModel(list: List<CategoriesDTO>): List<CategoryModel> {
        return list.map {
            CategoryModel(
                displayNameEncoded = it.displayNameEncoded,
                displayName = it.displayName,
                newestPublishedDate = it.newestPublishedDate
            )
        }
    }

    fun mapListBooksDTOToBookModel(result: BookResults): List<BookModel> {
        return result.books.map {book ->
            BookModel(
                isbn10 = book.primary_isbn10,
                displayName = book.title,
                productUrl = book.amazon_product_url,
                price = book.price,
                imageUrl = book.book_image,
                author = book.author,
                rating = book.rank,
                distributive = book.publisher,
                description = book.description,
            )
        }
    }
}