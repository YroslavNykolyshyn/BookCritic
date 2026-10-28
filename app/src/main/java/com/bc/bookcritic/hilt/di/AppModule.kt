package com.bc.bookcritic.hilt.di

import androidx.navigation3.runtime.NavKey
import com.bc.core.navigation.Home
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AppModule {
    @Provides
    fun provideStartDestination(): NavKey = Home
}