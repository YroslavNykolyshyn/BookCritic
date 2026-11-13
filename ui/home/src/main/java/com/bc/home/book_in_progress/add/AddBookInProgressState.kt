package com.bc.home.book_in_progress.add

import com.bc.model.repository.ReadingStatus

data class BookInProgressState(
    val bookInProgressItem: BookInProgressItem = BookInProgressItem(),
    val isSaving: Boolean = false,
)

data class BookInProgressItem(
    val coverPath: String? = "",
    val name: String = "",
    val author: String = "",
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val bookStatus: ReadingStatus = ReadingStatus.READING,
    val selectedCoverUri: String? = null,
    )