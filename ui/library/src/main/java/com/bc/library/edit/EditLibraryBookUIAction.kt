package com.bc.library.edit

import android.net.Uri

sealed interface EditLibraryBookUIAction {
    data object BackClicked : EditLibraryBookUIAction

    data class OnCoverSelected(val uri: Uri) : EditLibraryBookUIAction
    data object OnEditClicked : EditLibraryBookUIAction
    data object OnSaveClicked : EditLibraryBookUIAction

    data class OnTitleChanged(val value: String) : EditLibraryBookUIAction
    data class OnAuthorChanged(val value: String) : EditLibraryBookUIAction
    data class OnRatingChanged(val value: Float) : EditLibraryBookUIAction
    data class OnStartedDateChanged(val value: Long?) : EditLibraryBookUIAction
    data class OnFinishedDateChanged(val value: Long?) : EditLibraryBookUIAction
    data class OnSummaryChanged(val value: String) : EditLibraryBookUIAction
    data class OnNoteChanged(val value: String) : EditLibraryBookUIAction
}