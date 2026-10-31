package com.bc.model.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    fun observeBooks(): Flow<List<BookData>>

    suspend fun observeBooksCount(): Flow<Int>
    suspend fun getBook(id: String): BookData?
    suspend fun saveBook(
        book: BookData,
        selectedCoverUri: Uri?,
    )
    suspend fun deleteBook(book: BookData)
}