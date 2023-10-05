package com.testtask.polytech.data.repositories.remote.dto.book

data class BookResults(
    val bestsellers_date: String,
    val books: List<BookX>,
    val corrections: List<Any>,
    val display_name: String,
    val list_name: String,
    val list_name_encoded: String,
    val next_published_date: String,
    val normal_list_ends_at: Int,
    val previous_published_date: String,
    val published_date: String,
    val published_date_description: String,
    val updated: String
)