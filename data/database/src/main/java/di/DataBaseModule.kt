package di

import com.bc.database.BookDatabase
import android.content.Context
import androidx.room.Room
import com.bc.database.BookDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BookDatabase {
        return Room.databaseBuilder(
            context,
            BookDatabase::class.java,
            "books.db"
        ).build()
    }

    @Provides
    fun provideBookDao(
        database: BookDatabase
    ): BookDao = database.bookDao()
}