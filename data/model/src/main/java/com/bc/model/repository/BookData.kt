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
)

enum class ReadingStatus(val value: String) {
    TO_READ("To read"),
    READING("Reading"),
    ALREADY_READ("Already read"),
}