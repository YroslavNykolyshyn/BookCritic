package com.bc.book.ui.library.add

import android.net.Uri
import com.bc.model.repository.ReadingStatus

data class AddBookUiState(
    val isSaving: Boolean = false,
    val bookItem: AddBookItem = AddBookItem(),
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
    val coverUri: Uri? = null,
    val bookCost: String = "",
    val readBookCount: Int = 3,
)