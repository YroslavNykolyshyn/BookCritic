package com.bc.library.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.bc.library.R
import com.bc.core.theme.AppTheme.colors
import com.bc.core.utils.CameraImageProvider.createImageUri
import com.bc.core.widgets.AppBottomBarButton
import com.bc.core.widgets.AppCircularLoadingIndicator
import com.bc.core.widgets.AppModalBottomSheet
import com.bc.core.widgets.AppTopBar
import com.bc.core.widgets.BookContentLayout


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddBookScreen(
    state: AddBookUiState,
    onAction: (AddBookUIAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showImageSourceSheet by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let {
            onAction(AddBookUIAction.CoverSelected(it))
        }
    }

    var cameraUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            cameraUri?.let {
                onAction(AddBookUIAction.CoverSelected(it))
            }
        }
    }

    val isKeyBoardVisible = WindowInsets.isImeVisible

    Scaffold(
        modifier = modifier.imePadding(),
        containerColor = colors.background,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.add_book_title),
                onAction = {
                    onAction(AddBookUIAction.BackClicked)
                },
                enabled = !state.isSaving,
            )
        },
        bottomBar = {
            AppBottomBarButton(
                visible = isKeyBoardVisible,
                onSaveClicked = { onAction(AddBookUIAction.SaveClicked) },
                buttonEnabled = state.isSaving,
                readBookCount = state.readBookCount,
            )

            AppModalBottomSheet(
                visible = showImageSourceSheet,
                onGalleryClick = {
                    showImageSourceSheet = false

                    galleryLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
                onCameraClick = {
                    showImageSourceSheet = false

                    val uri = createImageUri(context)
                    cameraUri = uri
                    cameraLauncher.launch(uri)
                },
                onDismiss = {
                    showImageSourceSheet = false
                },
            )
        }
    ) { paddingValues ->
        BookContentLayout(
            paddingValues = paddingValues,
            title = state.bookItem.title,
            author = state.bookItem.author,
            rating = state.bookItem.rating,
            startedDate = state.bookItem.startedDate,
            finishedDate = state.bookItem.finishedDate,
            summary = state.bookItem.summary,
            note = state.bookItem.note,
            onTitleChanged = { onAction(AddBookUIAction.TitleChanged(it)) },
            onAuthorChanged = { onAction(AddBookUIAction.AuthorChanged(it)) },
            onRatingChanged = { onAction(AddBookUIAction.RatingChanged(it)) },
            onStartedDateChanged = { onAction(AddBookUIAction.StartedDateChanged(it)) },
            onFinishedDateChanged = { onAction(AddBookUIAction.FinishedDateChanged(it)) },
            onSummaryChanged = { onAction(AddBookUIAction.SummaryChanged(it)) },
            onNoteChanged = { onAction(AddBookUIAction.NoteChanged(it)) },
            onCoverChange = {
                showImageSourceSheet = true
            },
            selectedCoverUri = state.bookItem.selectedCoverUri,
        )

        if (state.isSaving) {
            AppCircularLoadingIndicator()
        }
    }
}
