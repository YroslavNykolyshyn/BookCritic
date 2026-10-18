package com.bc.model.repository

interface IBookRepository {
    suspend fun getBooks()
    suspend fun createBook()
    suspend fun deleteBook()
}