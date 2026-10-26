package com.bc.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

    @Query("SELECT * FROM book ORDER BY title COLLATE NOCASE ASC")
    fun observeBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM book WHERE id = :id")
    fun observeBookById(id: String): Flow<BookEntity?>

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBookById(id: String): BookEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(bookEntity: BookEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(bookEntities: List<BookEntity>)

    @Update
    suspend fun updateBook(bookEntity: BookEntity)

    @Query("DELETE FROM book WHERE id = :id")
    suspend fun deleteBook(id: String)

    @Query("DELETE FROM book")
    suspend fun deleteAllBooks()

    @Query(
        """
        SELECT * FROM book
        WHERE title LIKE '%' || :query || '%'
        ORDER BY title COLLATE NOCASE ASC
    """
    )
    fun searchBooks(query: String): Flow<List<BookEntity>>

    @Query("SELECT COUNT(*) FROM book")
    fun observeBookCount(): Flow<Int>
}