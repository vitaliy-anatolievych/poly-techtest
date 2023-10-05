package com.testtask.polytech.data.repositories.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoriesDTO(
    @SerializedName("list_name_encoded")
    val displayNameEncoded: String,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("newest_published_date")
    val newestPublishedDate: String,
)