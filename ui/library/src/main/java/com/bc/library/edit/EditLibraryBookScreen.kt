package com.bc.library.edit

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
import com.bc.core.theme.AppTheme.colors
import com.bc.core.utils.CameraImageProvider.createImageUri
import com.bc.core.widgets.AppBottomBarButton
import com.bc.core.widgets.AppCircularLoadingIndicator
import com.bc.core.widgets.AppModalBottomSheet
import com.bc.core.widgets.AppTopBar
import com.bc.library.BookContentLayout
import com.bc.library.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditLibraryBookScreen(
    state: EditLibraryBookUIState,
    onAction: (EditLibraryBookUIAction) -> Unit,
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
            onAction(EditLibraryBookUIAction.OnCoverSelected(it))
        }
    }

    var cameraUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            cameraUri?.let {
                onAction(EditLibraryBookUIAction.OnCoverSelected(it))
            }
        }
    }

    val isKeyBoardVisible = WindowInsets.isImeVisible

    Scaffold(
        modifier = modifier.imePadding(),
        containerColor = colors.background,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.edit_book_title),
                onAction = {
                    onAction(EditLibraryBookUIAction.BackClicked)
                },
                enabled = !state.isLoading,
            )
        },
        bottomBar = {
            AppBottomBarButton(
                visible = isKeyBoardVisible,
                onSaveClicked = {onAction(EditLibraryBookUIAction.OnSaveClicked)},
                buttonEnabled = state.isLoading,
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
            onTitleChanged = { onAction(EditLibraryBookUIAction.OnTitleChanged(it)) },
            onAuthorChanged = { onAction(EditLibraryBookUIAction.OnAuthorChanged(it)) },
            onRatingChanged = { onAction(EditLibraryBookUIAction.OnRatingChanged(it)) },
            onStartedDateChanged = { onAction(EditLibraryBookUIAction.OnStartedDateChanged(it)) },
            onFinishedDateChanged = { onAction(EditLibraryBookUIAction.OnFinishedDateChanged(it)) },
            onSummaryChanged = { onAction(EditLibraryBookUIAction.OnSummaryChanged(it)) },
            onNoteChanged = { onAction(EditLibraryBookUIAction.OnNoteChanged(it)) },
            onCoverChange = {
                showImageSourceSheet = true
            },
            selectedCoverUri = state.bookItem.selectedCoverUri,
        )

        if (state.isLoading) {
            AppCircularLoadingIndicator(transparentBackground = true)
        }
    }
}