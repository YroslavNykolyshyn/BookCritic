package com.bc.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val isbn: String? = null,
    val coverPath: String? = null,
    val description: String? = null,
)