package com.bc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bc.database.genre.BookGenreDao
import com.bc.database.genre.BookGenreEntity

@Database(
    entities = [BookEntity::class, BookGenreEntity::class], version = 1, exportSchema = true
)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
    abstract fun bookGenreDao(): BookGenreDao
}