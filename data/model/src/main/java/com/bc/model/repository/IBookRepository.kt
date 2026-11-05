package com.bc.model.repository

import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    fun observeBooks(): Flow<List<BookData>>

    suspend fun observeBooksCount(): Flow<Int>
    suspend fun getBookById(id: String): BookData?
    suspend fun saveBook(
        book: BookData,
        selectedCoverUri: Uri?,
    )
    suspend fun deleteBook(book: BookData)

    suspend fun updateBook(book: BookData)

    suspend fun toggleFavorite(bookId: String)
}