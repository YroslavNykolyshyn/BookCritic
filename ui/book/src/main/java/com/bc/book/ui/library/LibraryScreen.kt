package com.bc.book.ui.library

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.bc.core.theme.AppTheme
import com.bc.core.theme.AppTheme.colors
import com.bc.core.theme.MaterialAppTheme

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
            LibrarySearchBar(
                modifier = Modifier
                    .padding(
                        horizontal = AppTheme.dimensions.paddingTiny,
                        vertical = AppTheme.dimensions.paddingExtraSmall
                    ),
                query = state.searchQuery,
                onQueryChange = {
                    onAction(LibraryUIAction.SearchChanged(it))
                },
                onFilterClick = {
                    onAction(LibraryUIAction.FilterClicked)
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(LibraryUIAction.AddBookClicked)
                },
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Add book",
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(
                horizontal = AppTheme.dimensions.paddingBig,
                vertical = AppTheme.dimensions.paddingMedium,
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
                        text = "YOUR LITTLE ESCAPE",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.primary,
                        fontWeight = FontWeight.SemiBold,
                    )

                    Text(
                        text = "Already read",
                        style = MaterialTheme.typography.displaySmall,
                        color = colors.textPrimary,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = "A quiet record of the stories you've finished, " +
                                "with the covers and ratings that stayed with you.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.textSecondary,
                    )
                }
            }

            if (state.books.isEmpty()) {
                item {
                    //  EmptyLibraryState()
                }
            } else {
                items(
                    items = state.books,
                    key = { it.id },
                ) { book ->
                    LibraryBookItem(
                        book = book,
                    )
                }
            }
        }
    }
}

@Composable
private fun LibrarySearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(colors.surfaceVariant)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            tint = colors.primary,
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
                Box {
                    if (query.isEmpty()) {
                        Text(
                            text = "Search books, authors...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = colors.textHint,
                        )
                    }
                    innerTextField()
                }
            },
        )

        IconButton(onClick = onFilterClick) {
            Icon(
                imageVector = Icons.Outlined.Tune,
                contentDescription = "Filter books",
                tint = colors.primary,
            )
        }
    }
}

@Composable
private fun LibraryBookItem(
    book: LibraryBookUiModel,
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable(book.id) {
        mutableStateOf(false)
    }

    Row(
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
            .clickable {
                expanded = !expanded
            }
            .padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BookCover(book = book)

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = book.title,
                style = MaterialTheme.typography.titleLarge,
                color = colors.textPrimary,
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = book.author,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.textSecondary,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Rating",
                    tint = colors.primary,
                    modifier = Modifier.size(22.dp),
                )

                Text(
                    text = book.rating?.toString()?.removeSuffix(".0") ?: "—",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.primary,
                    fontWeight = FontWeight.Bold,
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically(),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    HorizontalDivider(color = colors.border)

                    Text(
                        text = book.bookSummary,
                        style = MaterialTheme.typography.titleSmall,
                        color = colors.textPrimary,
                    )

                    HorizontalDivider(color = colors.border)

                    Text(
                        text = book.bookOptionalSummary,
                        style = MaterialTheme.typography.titleSmall,
                        color = colors.textPrimary,
                    )
                }
            }
        }

        Icon(
            imageVector = if (expanded) {
                Icons.Default.ExpandLess
            } else {
                Icons.Default.ExpandMore
            },
            contentDescription = if (expanded) {
                "Collapse details"
            } else {
                "Expand details"
            },
            tint = colors.textSecondary,
        )
    }
}

@Composable
private fun BookCover(
    book: LibraryBookUiModel,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        model = book.coverPath.takeUnless { it.isNullOrBlank() },
        contentDescription = "${book.title} cover",
        modifier = modifier
            .size(width = 100.dp, height = 136.dp)
            .clip(RoundedCornerShape(5.dp)),
        contentScale = ContentScale.Crop,
        loading = {
            BookCoverPlaceholder()
        },
        error = {
            BookCoverPlaceholder()
        },
    )
}

@Composable
private fun BookCoverPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.surfaceVariant),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Outlined.Book,
            contentDescription = "No book cover",
            tint = colors.textSecondary,
            modifier = Modifier.size(40.dp),
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
                            label = {Text(text = "")},
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