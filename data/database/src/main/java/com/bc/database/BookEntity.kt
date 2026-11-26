package com.bc.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bc.database.genre.BookGenreEntity

@Entity(
    tableName = "book",
    foreignKeys = [
        ForeignKey(
            entity = BookGenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [Index("genreId")],
)
data class BookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val genreId: String? = null,
    val status: String,
    val rating: Float? = null,
    val startedDate: Long? = null,
    val finishedDate: Long? = null,
    val summary: String? = null,
    val note: String? = null,
    val coverPath: String? = null,
    val bookCost: Long? = null,
    val favorite: Boolean? = null,
    val totalPages: Long? = null,
    val currentPage: Long? = null,
)