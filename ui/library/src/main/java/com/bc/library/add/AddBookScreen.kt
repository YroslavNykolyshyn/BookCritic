package com.bc.library.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bc.library.R
import com.bc.core.theme.AppTheme
import com.bc.core.theme.AppTheme.colors
import com.bc.core.utils.CameraImageProvider.createImageUri
import com.bc.core.widgets.AppCircularLoadingIndicator
import com.bc.core.widgets.AppDataPickerField
import com.bc.core.widgets.AppStarRatingBar
import com.bc.core.widgets.AppTextInput
import com.bc.core.widgets.AppTopBar


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

    var cameraUri by remember { mutableStateOf<Uri?>(null) }

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
            if (!isKeyBoardVisible) {
                // Save
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimensions.paddingExtraSmall),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Button(
                        onClick = {
                            onAction(AddBookUIAction.SaveClicked)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(17.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors.primary,
                            contentColor = Color.White,
                        ),
                        enabled = !state.isSaving,
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text = "Finish and save",
                            fontWeight = FontWeight.Medium,
                        )
                    }

                    Text(
                        text = "Your read shelf has ${state.readBookCount} books. Make it ${state.readBookCount + 1}.",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.secondary,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            if (showImageSourceSheet) {
                ImageSourceBottomSheet(
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            HorizontalDivider(color = colors.border)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = AppTheme.dimensions.paddingBig,
                        vertical = AppTheme.dimensions.paddingExtraMedium,
                    ),
                verticalArrangement = Arrangement.spacedBy(
                    AppTheme.dimensions.paddingBig,
                ),
            ) {
                // Cover
                Row(
                    horizontalArrangement = Arrangement.spacedBy(22.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 100.dp, height = 136.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(colors.accent)
                            .clickable {
                                showImageSourceSheet = true
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        if (!state.bookItem.selectedCoverUri.isNullOrBlank()) {
                            AsyncImage(
                                model = state.bookItem.selectedCoverUri,
                                contentDescription = "Book cover",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Outlined.AddPhotoAlternate,
                                contentDescription = stringResource(R.string.add_book_cover),
                                tint = colors.primary,
                                modifier = Modifier.size(AppTheme.dimensions.iconSizeTiny),
                            )
                        }
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.book_cover_headline),
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.textPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Text(
                            text = stringResource(R.string.book_cover_description_upload),
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.secondary,
                        )
                    }
                }

                // Title
                AppTextInput(
                    label = stringResource(R.string.book_title_label),
                    value = state.bookItem.title,
                    placeholder = stringResource(R.string.book_title_placeholder),
                    onAction = {
                        onAction(AddBookUIAction.TitleChanged(it))
                    },
                )

                // Author
                AppTextInput(
                    label = stringResource(R.string.book_author_label),
                    value = state.bookItem.author,
                    placeholder = stringResource(R.string.book_author_placeholder),
                    onAction = {
                        onAction(AddBookUIAction.AuthorChanged(it))
                    },
                )

                // Personal Rating
                AppStarRatingBar(
                    label = stringResource(R.string.book_rating_label),
                    rating = state.bookItem.rating,
                    onRatingChanged = { onAction(AddBookUIAction.RatingChanged(it)) }
                )

                // Reading dates
                AppDataPickerField(
                    startDate = state.bookItem.startedDate,
                    endDate = state.bookItem.finishedDate,
                    onStartDateChange = { onAction(AddBookUIAction.StartedDateChanged(it)) },
                    onEndDateChange = { onAction(AddBookUIAction.FinishedDateChanged(it)) }
                )

                // Summary
                AppTextInput(
                    label = "Summary",
                    value = state.bookItem.summary,
                    placeholder = "What did you think about this book?",
                    singleLine = false,
                    minLines = 3,
                    height = 114.dp,
                    onAction = {
                        onAction(AddBookUIAction.SummaryChanged(it))
                    },
                )

                // Private note
                AppTextInput(
                    label = "A note for later · optional",
                    value = state.bookItem.note,
                    placeholder = "A thought to revisit later...",
                    singleLine = false,
                    minLines = 2,
                    height = 82.dp,
                    onAction = {
                        onAction(AddBookUIAction.NoteChanged(it))
                    },
                )
            }
        }

        if (state.isSaving) {
            AppCircularLoadingIndicator(transparentBackground = true)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageSourceBottomSheet(
    onGalleryClick: () -> Unit,
    onCameraClick: () -> Unit,
    onDismiss: () -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        scrimColor = Color.Black.copy(alpha = 0.32f),
        tonalElevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
        ) {
            Text(
                text = "Add book cover",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp),
            )

            ListItem(
                onClick = onGalleryClick,
                leadingContent = {
                    Icon(Icons.Outlined.PhotoLibrary, contentDescription = null)
                },
                supportingContent = { Text("Select an existing image") },
                content = { Text("Choose from gallery") },
            )

            ListItem(
                onClick = onCameraClick,
                leadingContent = {
                    Icon(Icons.Outlined.CameraAlt, contentDescription = null)
                },
                supportingContent = { Text("Use your camera") },
                content = { Text("Take a photo") },
            )
        }
    }

}