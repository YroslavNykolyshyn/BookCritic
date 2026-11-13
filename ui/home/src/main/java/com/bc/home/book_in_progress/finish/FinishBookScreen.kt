package com.bc.home.book_in_progress.finish

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.bc.core.widgets.AppBottomBarButton
import com.bc.core.widgets.BookContentLayout

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FinishBookScreen(
    state: FinishBookUIState,
    onAction: (FinishBookUIAction) -> Unit,
) {
    val isKeyBoardVisible = WindowInsets.isImeVisible
    Scaffold(
        bottomBar = {
            AppBottomBarButton(
                visible = isKeyBoardVisible,
                onSaveClicked = { onAction(FinishBookUIAction.OnConfirm) },
                buttonEnabled = state.isSaving,
            )
        }
    ) { paddingValues ->
        BookContentLayout(
            paddingValues = paddingValues,
            title = state.book?.title ?: "",
            author = state.book?.author ?: "",
            rating = state.rating,
            startedDate = state.book?.startedDate,
            finishedDate = state.book?.finishedDate,
            summary = state.summary,
            note = state.note,
            onTitleChanged = { },
            onAuthorChanged = {  },
            onRatingChanged = { onAction(FinishBookUIAction.OnRatingChanged(it)) },
            onStartedDateChanged = { },
            onFinishedDateChanged = { },
            onSummaryChanged = { onAction(FinishBookUIAction.OnSummaryChanged(it)) },
            onNoteChanged = { onAction(FinishBookUIAction.OnNoteChanged(it)) },
            onCoverChange = {

            },
            selectedCoverUri = "",
        )
    }
}