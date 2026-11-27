package com.bc.database

import androidx.room.Embedded
import androidx.room.Relation
import com.bc.database.genre.BookGenreEntity

data class BookWithGenre(
    @Embedded val book: BookEntity,

    @Relation(
        parentColumn = "genreId",
        entityColumn = "id",
    )
    val genre: BookGenreEntity?,
)