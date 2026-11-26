package com.bc.model.repository

data class BookGenre(
    val id: String,
    val name: String,
    val isCustom: Boolean = false,
)

object BookGenres {
    const val FICTION = "fiction"
    const val FANTASY = "fantasy"
    const val SCIENCE_FICTION = "science_fiction"
    // ... rest

    // Order here = order in the dropdown
    val ordered = listOf(
        FICTION, FANTASY, SCIENCE_FICTION,
    )
}