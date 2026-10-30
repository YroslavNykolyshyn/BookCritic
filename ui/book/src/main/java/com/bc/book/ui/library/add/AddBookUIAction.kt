package com.bc.book.ui.library.add

import android.net.Uri
import com.bc.model.repository.ReadingStatus


sealed interface AddBookUIAction {
    data object BackClicked : AddBookUIAction

    data class CoverSelected(val uri: Uri?) : AddBookUIAction
    data object SaveClicked : AddBookUIAction

    data class TitleChanged(val value: String) : AddBookUIAction
    data class AuthorChanged(val value: String) : AddBookUIAction
    data class StatusChanged(val value: ReadingStatus) : AddBookUIAction
    data class RatingChanged(val value: Float) : AddBookUIAction
    data class StartedDateChanged(val value: Long?) : AddBookUIAction
    data class FinishedDateChanged(val value: Long?) : AddBookUIAction
    data class SummaryChanged(val value: String) : AddBookUIAction
    data class NoteChanged(val value: String) : AddBookUIAction
}
