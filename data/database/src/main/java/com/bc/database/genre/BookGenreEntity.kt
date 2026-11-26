package com.bc.database.genre

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "book_genres",
    indices = [Index(value = ["name"], unique = true)],
)
data class BookGenreEntity(
    @PrimaryKey val id: String,
    val name: String,
    val isCustom: Boolean = false,
)