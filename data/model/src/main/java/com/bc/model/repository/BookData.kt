package com.bc.model.repository

data class BookData(
    val id: String,
    val title: String,
    val description: String? = "",
    val authors: List<String> = emptyList(),
    val rating: Float? = null,
    val coverPath: String? = "",
)