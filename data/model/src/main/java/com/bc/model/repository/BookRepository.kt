package com.bc.model.repository

import android.net.Uri
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

    override suspend fun getBook(id: String): BookData? =
        dao.getBookById(id)?.toDomain()

    override suspend fun saveBook(bookDataEntity: BookData) {
        dao.insertBook(bookDataEntity.toEntity())
    }

    override suspend fun deleteBook(book: BookData) {
        dao.deleteBook(book.id)
        book.coverPath?.let { path ->
            coverStorage.delete(path)
        }
    }

    override suspend fun saveBookCover(uri: Uri): String {
        return coverStorage.save(uri)
    }
}