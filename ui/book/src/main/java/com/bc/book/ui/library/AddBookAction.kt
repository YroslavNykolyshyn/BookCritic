package com.bc.book.ui.library

import android.net.Uri


sealed interface AddBookAction {
    data object BackClicked : AddBookAction

    data class CoverSelected(val uri: Uri?) : AddBookAction
    data object SaveClicked : AddBookAction

    data class TitleChanged(val value: String) : AddBookAction
    data class AuthorChanged(val value: String) : AddBookAction
    data class StatusChanged(val value: ReadingStatus) : AddBookAction
    data class RatingChanged(val value: Float) : AddBookAction
    data class StartedDateChanged(val value: Long?) : AddBookAction
    data class FinishedDateChanged(val value: Long?) : AddBookAction
    data class SummaryChanged(val value: String) : AddBookAction
    data class NoteChanged(val value: String) : AddBookAction
}

enum class ReadingStatus {
    TO_READ,
    READING,
    ALREADY_READ,
}
