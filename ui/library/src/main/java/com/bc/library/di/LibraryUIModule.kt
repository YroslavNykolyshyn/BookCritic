package com.bc.library.di

import com.bc.library.LibraryRouter
import com.bc.library.add.AddBookRouter
import com.bc.library.edit.EditLibraryBookRouter
import com.bc.core.navigation.AddLibraryBook
import com.bc.core.navigation.EditLibraryBook
import com.bc.core.navigation.EntryProviderInstaller
import com.bc.core.navigation.Library
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
class LibraryUIModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller() : EntryProviderInstaller = {
        entry<Library>{
            LibraryRouter()
        }
        entry<AddLibraryBook> {
            AddBookRouter()
        }
        entry<EditLibraryBook> { data ->
            EditLibraryBookRouter(data)
        }
    }
}