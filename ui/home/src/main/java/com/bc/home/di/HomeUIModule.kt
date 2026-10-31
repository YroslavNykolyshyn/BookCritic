package com.bc.home.di

import com.bc.core.navigation.EntryProviderInstaller
import com.bc.core.navigation.Home
import com.bc.home.HomeRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(ActivityRetainedComponent::class)
class HomeUIModule {
    @IntoSet
    @Provides
    fun provideEntryProviderInstaller() : EntryProviderInstaller = {
        entry<Home>{
            HomeRouter()
        }
    }
}