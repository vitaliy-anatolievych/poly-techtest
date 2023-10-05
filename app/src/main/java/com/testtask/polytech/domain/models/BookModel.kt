package com.testtask.polytech.domain.models


data class BookModel(
    val isbn10: String,
    val displayName: String,
    val productUrl: String,
    val price: String,
    val imageUrl: String,
    val author: String,
    val rating: Int,
    val distributive: String,
    val description: String,
)
