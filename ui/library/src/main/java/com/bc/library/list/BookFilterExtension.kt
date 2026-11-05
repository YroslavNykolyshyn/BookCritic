package com.bc.library.list

import com.bc.model.repository.BookData
import java.util.Locale

fun BookData.matchesSearchQuery(query: String): Boolean {
    val normalizedQuery = query.trim().lowercase(Locale.ROOT)

    if (normalizedQuery.isBlank()) return true

    return listOf(
        title,
        author,
        summary,
        note,
    ).any { field ->
        field.orEmpty()
            .lowercase(Locale.ROOT)
            .contains(normalizedQuery)
    }
}

fun List<BookData>.filterBooks(query: String): List<BookData> {
    if (query.isBlank()) return this

    return filter { book ->
        book.matchesSearchQuery(query)
    }
}

fun BookData.toUiModel(): LibraryBookUiModel {
    return LibraryBookUiModel(
        id = id,
        title = title,
        author = author,
        rating = rating,
        coverPath = coverPath,
        bookSummary = summary ?: "",
        bookOptionalSummary = note ?: "",
        isFavorite = isFavorite,
    )
}