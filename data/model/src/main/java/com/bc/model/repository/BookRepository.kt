package com.bc.model.repository

import android.net.Uri
import android.util.Log
import com.bc.database.BookDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import toDomain
import toEntity
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val dao: BookDao,
    private val coverStorage: BookCoverStorage,
) : IBookRepository {
    override fun observeBooks(): Flow<List<BookData>> =
        dao.observeBooks().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun observeBooksCount(): Flow<Int> {
        return dao.observeBookCount()
    }

    override suspend fun getBookById(id: String): BookData? =
        dao.getBookById(id)?.toDomain()

    override suspend fun saveBook(
        book: BookData,
        selectedCoverUri: Uri?,
    ) {
        val oldCoverPath = book.coverPath

        val savedCoverPath = selectedCoverUri?.let {
            coverStorage.save(it)
        }

        try {
            val bookToSave = book.copy(
                coverPath = savedCoverPath ?: oldCoverPath,
            )

            dao.insertBook(bookToSave.toEntity())

            if (savedCoverPath != null && oldCoverPath != null) {
                runCatching {
                    coverStorage.delete(oldCoverPath)
                }.onFailure { cleanupError ->
                    Log.e(
                        TAG,
                        "Failed to delete old book cover",
                        cleanupError,
                    )
                }
            }
        } catch (e: Exception) {
            savedCoverPath?.let { path ->
                runCatching {
                    coverStorage.delete(path)
                }.onFailure { cleanupError ->
                    Log.e(
                        TAG,
                        "Failed to clean up new cover after save failure",
                        cleanupError,
                    )
                }
            }

            throw e
        }
    }

    override suspend fun deleteBook(book: BookData) {
        dao.deleteBook(book.id)
        book.coverPath?.let { path ->
            coverStorage.delete(path)
        }
    }

    override suspend fun updateBook(book: BookData) {
        dao.updateBook(book.toEntity())
    }

    override suspend fun toggleFavorite(bookId: String) {
        dao.toggleFavorite(bookId)
    }

    companion object {
        private const val TAG = "BookRepository"
    }
}