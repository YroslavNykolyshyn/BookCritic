package com.bc.bookcritic.nav3

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import com.bc.core.navigation.Home
import com.bc.core.navigation.INavigation
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Navigation @Inject constructor() : INavigation {

    override val mainBackStack: SnapshotStateList<NavKey> = mutableStateListOf(Home)

    override fun goTo(destination: NavKey) {
        mainBackStack.add(destination)
    }

    override fun goBack() {
        when (mainBackStack.size) {
            1 -> Log.d("Navigation", "Go back rejected!")
            else -> mainBackStack.removeLastOrNull()
        }
    }
}