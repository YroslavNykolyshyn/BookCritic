package com.bc.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bc.core.theme.AppTheme
import com.bc.core.theme.AppTheme.colors
import com.bc.core.R as CoreR

@Composable
fun BookContentLayout(
    isEditable: Boolean = true,
    title: String,
    author: String,
    rating: Float,
    startedDate: Long?,
    finishedDate: Long?,
    summary: String,
    note: String,
    onTitleChanged: (String) -> Unit,
    onAuthorChanged: (String) -> Unit,
    onRatingChanged: (Float) -> Unit,
    onStartedDateChanged: (Long?) -> Unit,
    onFinishedDateChanged: (Long?) -> Unit,
    onSummaryChanged: (String) -> Unit,
    onNoteChanged: (String) -> Unit,
    onCoverChange: () -> Unit,
    paddingValues: PaddingValues,
    selectedCoverUri: String?,
    isPinned: Boolean = false,
) {
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
                        .then(
                            if (isEditable) {
                                Modifier.clickable(onClick = onCoverChange)
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    if (!selectedCoverUri.isNullOrBlank()) {
                        AsyncImage(
                            model = selectedCoverUri,
                            contentDescription = "Book cover",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.AddPhotoAlternate,
                            contentDescription = stringResource(CoreR.string.add_book_cover),
                            tint = colors.primary,
                            modifier = Modifier.size(AppTheme.dimensions.iconSizeTiny),
                        )
                    }
                }

                if (isEditable) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Text(
                            text = stringResource(CoreR.string.book_cover_headline),
                            style = MaterialTheme.typography.titleLarge,
                            color = colors.textPrimary,
                            fontWeight = FontWeight.SemiBold,
                        )

                        Text(
                            text = stringResource(CoreR.string.book_cover_description_upload),
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.secondary,
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = null,
                                tint = colors.primary,
                            )

                            Text(
                                text = rating.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                color = colors.textPrimary,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }

                        if (isPinned) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = null,
                                    tint = Color(0xFFAD3659),
                                )

                                Text(
                                    text = stringResource(CoreR.string.book_pinned_label),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = colors.secondary,
                                )
                            }
                        }
                    }
                }
            }

            // Title
            AppTextInput(
                label = stringResource(CoreR.string.book_title_label),
                value = title,
                placeholder = stringResource(CoreR.string.book_title_placeholder),
                onAction = {
                    onTitleChanged(it)
                },
                readOnly = !isEditable,
            )

            // Author
            AppTextInput(
                label = stringResource(CoreR.string.book_author_label),
                value = author,
                placeholder = stringResource(CoreR.string.book_author_placeholder),
                onAction = {
                    onAuthorChanged(it)
                },
                readOnly = !isEditable,
            )

            // Personal Rating
            if (isEditable) {
                AppStarRatingBar(
                    label = stringResource(CoreR.string.book_rating_label),
                    rating = rating,
                    onRatingChanged = { onRatingChanged(it) },
                )
            }

            // Reading dates
            if (isEditable || (finishedDate != null && startedDate != null)) {
                AppDataPickerField(
                    startDate = startedDate,
                    endDate = finishedDate,
                    onStartDateChange = { onStartedDateChanged(it) },
                    onEndDateChange = { onFinishedDateChanged(it) },
                    enabled = isEditable,
                )
            }

            // Summary
            if (isEditable || summary.isNotBlank()) {
                AppTextInput(
                    label = "Summary",
                    value = summary,
                    placeholder = "What did you think about this book?",
                    singleLine = false,
                    minLines = 3,
                    height = 114.dp,
                    onAction = {
                        onSummaryChanged(it)
                    },
                    readOnly = !isEditable,
                )
            }

            // Private note
            if (isEditable || note.isNotBlank()) {
                AppTextInput(
                    label = "A note for later · optional",
                    value = note,
                    placeholder = "A thought to revisit later...",
                    singleLine = false,
                    minLines = 2,
                    height = 82.dp,
                    onAction = {
                        onNoteChanged(it)
                    },
                    readOnly = !isEditable,
                )
            }
        }
    }
}