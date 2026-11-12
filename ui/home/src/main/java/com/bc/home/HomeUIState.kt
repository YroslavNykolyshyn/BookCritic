package com.bc.home

import com.bc.model.repository.ReadingStatus
import java.time.LocalDate

data class HomeUIState(
    val isLoading: Boolean = false,
    val bookInProgress: Boolean = true,
    val error: String? = null,
    val currentPage: Int = 0,
)

data class BookInProgress(
    val id: String,
    val coverPath: String?,
    val name: String,
    val author: String,
    val startedAt: LocalDate,
    val currentPage: Int,
    val totalPages: Int,
    val bookStatus: ReadingStatus = ReadingStatus.READING,
)

data class BookCompletionDetails(
    val rating: Float = 0f,
    val summary: String = "",
    val note: String = "",
)