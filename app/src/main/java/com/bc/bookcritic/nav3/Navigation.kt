package com.bc.bookcritic.nav3

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import com.bc.core.navigation.Home
import com.bc.core.navigation.INavigation
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Navigation @Inject constructor() : INavigation {

    override val mainBackStack: List<NavKey>
        field = mutableStateListOf<NavKey>(Home)

    override fun goTo(destination: NavKey) {
        mainBackStack.add(destination)
    }

    override fun goBack() {
        if (mainBackStack.size > 1) {
            mainBackStack.removeLastOrNull()
        }
    }
}