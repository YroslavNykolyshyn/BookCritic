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
import com.bc.model.repository.BookGenre


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddBookScreen(
    modifier: Modifier = Modifier,
    state: AddBookUiState,
    genres: List<BookGenre> = emptyList(),
    onAction: (AddBookUIAction) -> Unit,
) {
    var showImageSourceSheet by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let {
            onAction(AddBookUIAction.OnCoverSelected(it))
        }
    }

    var cameraUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            cameraUri?.let {
                onAction(AddBookUIAction.OnCoverSelected(it))
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
                    onAction(AddBookUIAction.OnBackClicked)
                },
                enabled = !state.isSaving,
            )
        },
        bottomBar = {
            AppBottomBarButton(
                visible = isKeyBoardVisible,
                onSaveClicked = { onAction(AddBookUIAction.OnSaveClicked) },
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
            genres = genres,
            selectedGenreId = state.bookItem.genre?.id,
            rating = state.bookItem.rating,
            startedDate = state.bookItem.startedDate,
            finishedDate = state.bookItem.finishedDate,
            summary = state.bookItem.summary,
            note = state.bookItem.note,
            onTitleChanged = { onAction(AddBookUIAction.OnTitleChanged(it)) },
            onAuthorChanged = { onAction(AddBookUIAction.OnAuthorChanged(it)) },
            onRatingChanged = { onAction(AddBookUIAction.OnRatingChanged(it)) },
            onStartedDateChanged = { onAction(AddBookUIAction.OnStartedDateChanged(it)) },
            onFinishedDateChanged = { onAction(AddBookUIAction.OnFinishedDateChanged(it)) },
            onSummaryChanged = { onAction(AddBookUIAction.OnSummaryChanged(it)) },
            onNoteChanged = { onAction(AddBookUIAction.OnNoteChanged(it)) },
            onCoverChange = {
                showImageSourceSheet = true
            },
            onGenreSelected = {
                onAction(AddBookUIAction.OnGenreChanged(it))
            },
            onAddCustomGenre = {
                onAction(AddBookUIAction.OnCustomGenreAdded(it))
            },
            selectedCoverUri = state.bookItem.selectedCoverUri,
        )

        if (state.isSaving) {
            AppCircularLoadingIndicator()
        }
    }
}
