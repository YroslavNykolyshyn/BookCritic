package com.bc.book.ui.library

import android.net.Uri

data class AddBookUiState(
    val title: String = "",
    val author: String = "",
    val status: ReadingStatus = ReadingStatus.TO_READ,
    val rating: Float = 0f,
    val startedDate: Long? = null,
    val finishedDate: Long? = null,
    val summary: String = "",
    val note: String = "",
    val coverUri: Uri? = null,
    val readBookCount: Int = 3,
)