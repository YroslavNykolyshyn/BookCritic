package com.bc.bookcritic.hilt.di

import com.bc.bookcritic.nav3.Navigation
import com.bc.core.navigation.INavigation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class NavigationModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindNavigation(
        implementation: Navigation
    ): INavigation
}