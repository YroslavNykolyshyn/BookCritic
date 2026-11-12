package com.bc.home.book_in_progress.add

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bc.core.R
import com.bc.core.theme.AppTheme
import com.bc.core.theme.AppTheme.colors
import com.bc.core.widgets.AppNumberInput
import com.bc.core.widgets.AppTextInput
import com.bc.core.widgets.AppTopBar
import com.bc.home.book_in_progress.BookInProgressUIAction

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookInProgressScreen(
    state: BookInProgressState,
    onAction: (BookInProgressUIAction) -> Unit,
) {
    var showImageSourceSheet by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let {
            onAction(BookInProgressUIAction.OnCoverSelected(it))
        }
    }

    var cameraUri by rememberSaveable { mutableStateOf<Uri?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            cameraUri?.let {
                onAction(BookInProgressUIAction.OnCoverSelected(it))
            }
        }
    }

    val isKeyBoardVisible = WindowInsets.isImeVisible

    val state = state.bookInProgressItem

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.add_book_in_progress),
                onAction = {
                    onAction(BookInProgressUIAction.OnBackClicked)
                },
            )
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
                //TODO() Add future book observer

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
                            .clickable(onClick = { showImageSourceSheet = true }),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (!state?.selectedCoverUri.isNullOrBlank()) {
                            AsyncImage(
                                model = state.selectedCoverUri,
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
                    value = state.name,
                    placeholder = stringResource(R.string.book_title_placeholder),
                    onAction = {
                        onAction(BookInProgressUIAction.OnTitleChanged(it))
                    },
                )

                // Author
                AppTextInput(
                    label = stringResource(R.string.book_author_label),
                    value = state.author,
                    placeholder = stringResource(R.string.book_author_placeholder),
                    onAction = {
                        onAction(BookInProgressUIAction.OnAuthorChanged(it))
                    },
                )

                // Page Count
                AppNumberInput(
                    label = stringResource(R.string.book_page_count_label),
                    value = state.totalPages,
                    placeholder = stringResource(R.string.book_page_count_placeholder),
                    onAction = {
                        onAction(BookInProgressUIAction.OnPageChanged(it))
                    }
                )

            }
        }
    }
}