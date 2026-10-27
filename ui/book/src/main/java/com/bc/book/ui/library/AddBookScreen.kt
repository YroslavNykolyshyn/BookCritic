package com.bc.book.ui.library

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bc.book.R
import com.bc.core.theme.AppTheme
import com.bc.core.widgets.AppDataPickerField
import com.bc.core.widgets.AppStarRatingBar
import com.bc.core.widgets.AppTextInput
import com.bc.core.widgets.AppTopBar


@Composable
fun AddBookScreen(
    state: AddBookUiState,
    onAction: (AddBookAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let {
            onAction(AddBookAction.CoverSelected(it))
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .imePadding(),
    ) {
        AppTopBar(
            title = stringResource(R.string.add_book_title),
            onAction = {
                onAction(AddBookAction.BackClicked)
            },
        )

        HorizontalDivider(color = AppTheme.colors.border)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = AppTheme.dimensions.paddingBig)
                .padding(
                    top = AppTheme.dimensions.paddingExtraMedium,
                    bottom = AppTheme.dimensions.paddingBig
                ),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimensions.paddingBig),
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
                        .background(AppTheme.colors.beige)
                        .clickable {
                            imagePickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (state.coverUri != null) {
                        AsyncImage(
                            model = state.coverUri,
                            contentDescription = "Book cover",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.AddPhotoAlternate,
                            contentDescription = stringResource(R.string.add_book_cover),
                            tint = AppTheme.colors.brown,
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
                        color = AppTheme.colors.textColor,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Text(
                        text = stringResource(R.string.book_cover_description_upload),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.colors.taupe,
                    )
                }
            }

            // Title
            AppTextInput(
                label = stringResource(R.string.book_title_label),
                value = state.title,
                placeholder = stringResource(R.string.book_title_placeholder),
                onAction = {
                    onAction(AddBookAction.TitleChanged(it))
                },
            )

            // Author
            AppTextInput(
                label = stringResource(R.string.book_author_label),
                value = state.author,
                placeholder = stringResource(R.string.book_author_placeholder),
                onAction = {
                    onAction(AddBookAction.AuthorChanged(it))
                },
            )

            // Personal Rating
            AppStarRatingBar(
                label = stringResource(R.string.book_rating_label),
                rating = state.rating,
                onRatingChanged = { onAction(AddBookAction.RatingChanged(it)) }
            )

            // Reading dates
            AppDataPickerField(
                startDate = state.startedDate,
                endDate = state.finishedDate,
                onStartDateChange = { onAction(AddBookAction.StartedDateChanged(it)) },
                onEndDateChange = { onAction(AddBookAction.FinishedDateChanged(it)) }
            )

            // Summary
            AppTextInput(
                label = "Summary",
                value = state.summary,
                placeholder = "What did you think about this book?",
                singleLine = false,
                minLines = 3,
                height = 114.dp,
                onAction = {
                    onAction(AddBookAction.SummaryChanged(it))
                },
            )

            // Private note
            AppTextInput(
                label = "A note for later · optional",
                value = state.note,
                placeholder = "A thought to revisit later...",
                singleLine = false,
                minLines = 2,
                height = 82.dp,
                onAction = {
                    onAction(AddBookAction.NoteChanged(it))
                },
            )

            // Save
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Button(
                    onClick = {
                        onAction(AddBookAction.SaveClicked)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(17.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colors.brown,
                        contentColor = Color.White,
                    ),
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
                    color = AppTheme.colors.taupe,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}