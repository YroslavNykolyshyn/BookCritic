package com.bc.library.list

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.bc.core.theme.AppTheme
import com.bc.core.theme.AppTheme.colors
import com.bc.core.theme.MaterialAppTheme
import com.bc.core.widgets.AppCircularLoadingIndicator
import com.bc.core.widgets.SwipeableBookItem

@Composable
fun LibraryScreen(
    state: LibraryUiState,
    onAction: (LibraryUIAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = colors.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            LibraryToolbar(
                query = state.searchQuery,
                onQueryChange = {
                    onAction(LibraryUIAction.OnSearchChanged(it))
                },
                onSearchStopped = {
                    onAction(LibraryUIAction.OnSearchStopped)
                },
                onFilterClick = {
                    onAction(LibraryUIAction.OnFilterClicked)
                },
                onAddBookClick = {
                    onAction(LibraryUIAction.OnAddBookClicked)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = AppTheme.dimensions.paddingSmall,
                        vertical = AppTheme.dimensions.paddingExtraSmall,
                    ),
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading -> {
                AppCircularLoadingIndicator()
            }

            state.books.isEmpty() -> {
                EmptyBooksContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(
                        start = AppTheme.dimensions.paddingBig,
                        end = AppTheme.dimensions.paddingBig,
                        top = AppTheme.dimensions.paddingMedium,
                        bottom = 56.dp + AppTheme.dimensions.paddingExtraLarge,
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        AppTheme.dimensions.paddingBig,
                    ),
                ) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Text(
                                text = "Already read",
                                style = MaterialTheme.typography.displaySmall,
                                color = colors.textPrimary,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    items(
                        items = state.books,
                        key = { it.id },
                    ) { book ->
                        SwipeableBookItem(
                            isFavorite = book.isFavorite,
                            bookId = book.id,
                            onPinClick = {
                                onAction(LibraryUIAction.OnFavoriteBookClicked(book.id))
                            },
                        ) { cardModifier ->
                            LibraryBookItem(
                                onClick = {onAction(LibraryUIAction.OnViewBookClicked(book.id))},
                                book = book,
                                modifier = cardModifier,
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun LibraryBookItem(
    book: LibraryBookUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surfaceVariant)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                ),
            )
            .clickable {
              onClick()
            }
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BookCover(book = book)

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleMedium,
                color = colors.textPrimary,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
            )

            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.textSecondary,
                maxLines = 1,
                overflow = TextOverflow.Clip,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Rating",
                    tint = colors.primary,
                )

                Text(
                    text = book.rating?.toString()?.removeSuffix(".0") ?: "—",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.primary,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun BookCover(
    book: LibraryBookUiModel,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = book.coverPath?.takeIf { it.isNotBlank() },
        contentDescription = "${book.title} cover",
        modifier = modifier
            .size(width = 76.dp, height = 104.dp)
            .clip(RoundedCornerShape(5.dp)),
        contentScale = ContentScale.Crop,
    )
}

@Composable
private fun EmptyBooksContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.MenuBook,
            contentDescription = null,
            modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Your library is empty",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Add your first book and start tracking your reading journey.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun LibraryToolbar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchStopped: () -> Unit,
    onFilterClick: () -> Unit,
    onAddBookClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val shape = RoundedCornerShape(24.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 56.dp)
            .background(
                color = colors.bottomBarBackground,
                shape = shape,
            )
            .border(
                width = 1.dp,
                color = colors.border,
                shape = shape,
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            tint = colors.secondary,
        )

        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = colors.textPrimary,
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search books, authors...",
                            color = colors.textHint,
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                        )
                    }
                    innerTextField()
                }
            },
        )

        AnimatedContent(
            targetState = query.isNotBlank(),
            transitionSpec = {
                fadeIn() + expandHorizontally() togetherWith
                        fadeOut() + shrinkHorizontally()
            },
            label = "SearchActions",
        ) { isSearching ->
            if (isSearching) {
                ToolbarActionButton(
                    icon = Icons.Default.Close,
                    description = "Clear search",
                    onClick = {
                        onQueryChange("")
                        onSearchStopped()
                    },
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ToolbarActionButton(
                        icon = Icons.Outlined.Tune,
                        description = "Filter books",
                        onClick = onFilterClick,
                    )

                    ToolbarActionButton(
                        icon = Icons.Outlined.Add,
                        description = "Add book",
                        onClick = onAddBookClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun ToolbarActionButton(
    icon: ImageVector,
    description: String,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = colors.primary,
            containerColor = colors.surfaceVariant,
        ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LibraryScreenPreview() {
    val items = listOf("item", "item", "item")
    MaterialAppTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = colors.bottomBarBackground,
                    contentColor = colors.secondary,
                    windowInsets = NavigationBarDefaults.windowInsets,
                ) {
                    items.forEach { _ ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Image,
                                    contentDescription = "item",
                                )
                            },
                            selected = false,
                            onClick = {},
                            enabled = false,
                            label = { Text(text = "") },
                            alwaysShowLabel = false,
                        )
                    }
                }
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier.padding(innerPadding),
            ) {
                LibraryScreen(
                    state = LibraryUiState(
                        isLoading = false,
                        searchQuery = "",
                        books = listOf(
                            LibraryBookUiModel(
                                id = "1",
                                title = "The Midnight Library",
                                author = "Matt Haig",
                                rating = 4.5f,
                                coverPath = null,
                                bookSummary = "",
                                bookOptionalSummary = "",
                            ),
                            LibraryBookUiModel(
                                id = "2",
                                title = "Atomic Habits",
                                author = "James Clear",
                                rating = 5f,
                                coverPath = null,
                                bookSummary = "",
                                bookOptionalSummary = "",
                            ),
                            LibraryBookUiModel(
                                id = "3",
                                title = "1984",
                                author = "George Orwell",
                                rating = 4f,
                                coverPath = null,
                                bookSummary = "",
                                bookOptionalSummary = "",
                            ),
                        ),
                    ),
                    onAction = {},
                )
            }
        }
    }
}