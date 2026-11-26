package com.bc.database.genre

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookGenreDao {

    @Query("SELECT * FROM book_genres ORDER BY name COLLATE NOCASE ASC")
    fun observeGenres(): Flow<List<BookGenreEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenres(genres: List<BookGenreEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGenre(genre: BookGenreEntity)

    @Query("SELECT * FROM book_genres WHERE id = :id LIMIT 1")
    suspend fun getGenre(id: String): BookGenreEntity?

    @Delete
    suspend fun deleteGenre(genre: BookGenreEntity)
}