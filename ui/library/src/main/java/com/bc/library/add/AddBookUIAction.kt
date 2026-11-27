package com.bc.library.add

import android.net.Uri
import com.bc.model.repository.BookGenre
import com.bc.model.repository.ReadingStatus


sealed interface AddBookUIAction {
    data object OnBackClicked : AddBookUIAction

    data class OnCoverSelected(val uri: Uri) : AddBookUIAction
    data object OnSaveClicked : AddBookUIAction

    data class OnTitleChanged(val value: String) : AddBookUIAction
    data class OnAuthorChanged(val value: String) : AddBookUIAction
    data class OnStatusChanged(val value: ReadingStatus) : AddBookUIAction
    data class OnRatingChanged(val value: Float) : AddBookUIAction
    data class OnStartedDateChanged(val value: Long?) : AddBookUIAction
    data class OnFinishedDateChanged(val value: Long?) : AddBookUIAction
    data class OnSummaryChanged(val value: String) : AddBookUIAction
    data class OnNoteChanged(val value: String) : AddBookUIAction
    data class OnGenreChanged(val genre: BookGenre?) : AddBookUIAction
    data class OnCustomGenreAdded(val name: String) : AddBookUIAction
}
