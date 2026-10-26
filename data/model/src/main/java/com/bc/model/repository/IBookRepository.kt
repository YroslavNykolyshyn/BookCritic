package com.bc.model.repository

import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    fun observeBooks(): Flow<List<BookData>>
    suspend fun getBook(id: String): BookData?
    suspend fun saveBook(bookDataEntity: BookData)
    suspend fun deleteBook(id: String)
}