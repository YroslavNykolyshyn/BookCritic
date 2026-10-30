package com.bc.book.ui.di

import com.bc.book.ui.home.HomeRouter
import com.bc.book.ui.library.LibraryRouter
import com.bc.book.ui.library.add.AddBookRouter
import com.bc.core.navigation.AddLibraryBook
import com.bc.core.navigation.BookDetails
import com.bc.core.navigation.Home
import com.bc.core.navigation.EntryProviderInstaller
import com.bc.core.navigation.Library
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
class BookUIModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller() : EntryProviderInstaller = {
        entry<Home>{
            HomeRouter()
        }
        entry<Library>{
            LibraryRouter()
        }
        entry<AddLibraryBook> {
            AddBookRouter()
        }
    }
}