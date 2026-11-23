package com.bc.model.repository

data class BookData(
    val id: String,
    val title: String,
    val author: String,
    val status: ReadingStatus = ReadingStatus.ALREADY_READ,
    val rating: Float? = null,
    val startedDate: Long? = null,
    val finishedDate: Long? = null,
    val summary: String? = null,
    val note: String? = null,
    val coverPath: String? = null,
    val bookCost: Long? = null,
    val isFavorite: Boolean = false,
    val totalPages: Long? = null,
    val currentPage: Long? = null,
)

enum class ReadingStatus(val value: String) {
    TO_READ("to_read"),
    READING("reading"),
    ALREADY_READ("already_read"),
}