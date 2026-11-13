package com.bc.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bc.core.theme.AppTheme
import com.bc.core.widgets.AppAddBookCard
import com.bc.core.widgets.AppCircularLoadingIndicator
import com.bc.home.book_in_progress.add.BookInProgressItem
import com.bc.home.book_in_progress.add.BookInProgressState
import kotlin.math.roundToInt


@Composable
fun HomeScreen(
    uiState: HomeUIState,
    onAction: (HomeUIAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                AppCircularLoadingIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.dimensions.paddingBig),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Your current chapter",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                if (uiState.bookInProgressState?.bookInProgressItem == null) {
                    AppAddBookCard(
                        onClick = {
                            onAction(HomeUIAction.OnAddBookInProgress)
                        },
                        label = "Add a book in progress",
                        description = "Track your reading journey"
                    )
                } else {
                    BookInProgressItem(
                        book = uiState.bookInProgressState.bookInProgressItem,
                        onUpdateProgress = {

                        },
                        onFinishBook = {

                        }
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Your to-read shelf",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                AppAddBookCard(
                    onClick = {},
                    label = "Add a future book",
                    description = "Track your reading journey"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "A few pages. A little more room for yourself.",
                        style = MaterialTheme.typography.titleSmall,
                        color = AppTheme.colors.secondary,
                    )
                }
            }
        }
    }
}


@Composable
private fun BookInProgressItem(
    book: BookInProgressItem,
    modifier: Modifier = Modifier,
    onUpdateProgress: () -> Unit,
    onFinishBook: () -> Unit,
) {
    val colors = AppTheme.colors

    val progress = remember(book.currentPage, book.totalPages) {
        if (book.totalPages > 0) {
            (book.currentPage.toFloat() / book.totalPages)
                .coerceIn(0f, 1f)
        } else {
            0f
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(colors.surfaceVariant)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            )
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (book.coverPath.isNullOrEmpty()) {
                Icon(
                    modifier = Modifier.size(
                        width = 116.dp,
                        height = 164.dp,
                    ), imageVector = Icons.Default.Book, contentDescription = null
                )
            } else {
                BookCover(
                    book = book,
                    modifier = Modifier.size(
                        width = 116.dp,
                        height = 164.dp,
                    ),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = stringResource(R.string.reading_now).uppercase(),
                    style = MaterialTheme.typography.titleSmall,
                    color = colors.primary,
                    fontWeight = FontWeight.SemiBold,
                )

                Text(
                    text = book.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontFamily = FontFamily.Serif,
                    ),
                    color = colors.textPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = book.author,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(
                        R.string.reading_progress_pages,
                        book.currentPage,
                        book.totalPages,
                    ),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textPrimary,
                )

                Text(
                    text = "${(progress * 100).roundToInt()}%",
                    style = MaterialTheme.typography.titleLarge,
                    color = colors.textPrimary,
                    fontWeight = FontWeight.Bold,
                )
            }

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(CircleShape),
                color = colors.primary,
                trackColor = colors.border,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ReadingActionButton(
                text = stringResource(R.string.update_progress),
                icon = Icons.AutoMirrored.Outlined.MenuBook,
                onClick = { onUpdateProgress() },
                modifier = Modifier.weight(1f),
                primary = true,
            )

            ReadingActionButton(
                text = stringResource(R.string.finish_book),
                icon = Icons.Default.Check,
                onClick = { onFinishBook() },
                modifier = Modifier.weight(1f),
                primary = false,
            )
        }
    }
}


@Composable
private fun ReadingActionButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    primary: Boolean,
) {
    val colors = AppTheme.colors

    val backgroundColor = if (primary) {
        colors.primary
    } else {
        colors.surface
    }

    val contentColor = if (primary) {
        colors.surface
    } else {
        colors.primary
    }

    Surface(
        onClick = onClick,
        modifier = modifier.heightIn(min = 64.dp),
        shape = RoundedCornerShape(24.dp),
        color = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 16.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentColor,
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = text,
                color = contentColor,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
            )
        }
    }
}


@Composable
private fun BookCover(
    book: BookInProgressItem,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = book.coverPath?.takeIf { it.isNotBlank() },
        contentDescription = "${book.coverPath} cover",
        modifier = modifier
            .clip(RoundedCornerShape(5.dp)),
        contentScale = ContentScale.Crop,
    )
}

@Preview
@Composable
fun ReadingActionButtonPreview() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ReadingActionButton(
            text = stringResource(R.string.update_progress),
            icon = Icons.AutoMirrored.Outlined.MenuBook,
            onClick = {  },
            modifier = Modifier.weight(1f),
            primary = true,
        )

        ReadingActionButton(
            text = stringResource(R.string.finish_book),
            icon = Icons.Default.Check,
            onClick = {  },
            modifier = Modifier.weight(1f),
            primary = false,
        )

    }
}