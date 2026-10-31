package com.bc.library.add

import com.bc.model.repository.ReadingStatus

data class AddBookUiState(
    val isSaving: Boolean = false,
    val bookItem: AddBookItem = AddBookItem(),
    val readBookCount: Int = 0,
    )

data class AddBookItem(
    val title: String = "",
    val author: String = "",
    val status: ReadingStatus = ReadingStatus.ALREADY_READ,
    val rating: Float = 0f,
    val startedDate: Long? = null,
    val finishedDate: Long? = null,
    val summary: String = "",
    val note: String = "",
    val selectedCoverUri: String? = null,
    val bookCost: String = "",
)