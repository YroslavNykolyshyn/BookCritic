package com.bc.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BookEntity::class], version = 1, exportSchema = true
)
abstract class BookDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}