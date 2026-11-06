package com.bc.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val status: String,
    val rating: Float? = null,
    val startedDate: Long? = null,
    val finishedDate: Long? = null,
    val summary: String? = null,
    val note: String? = null,
    val coverPath: String? = null,
    val bookCost: Long? = null,
    val favorite: Boolean? = null,
)