package com.bc.bookcritic

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.bc.bookcritic.nav3.bottom_nav.BottomBarNavigation
import com.bc.core.navigation.EntryProviderInstaller
import com.bc.core.navigation.Home
import com.bc.core.navigation.Library
import com.bc.core.navigation.Stats
import com.bc.core.navigation.INavigation

@Composable
fun BookCriticApp(
    navigation: INavigation,
    entryProviderInstallers: Set<EntryProviderInstaller>,
) {
    val currentKey = navigation.mainBackStack.lastOrNull()

    val showBottomBar = currentKey in setOf(
        Home,
        Library,
        Stats,
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBarNavigation(
                    selectedDestination = currentKey,
                    onDestinationClick = navigation::goTo,
                )
            }
        },
    ) { innerPadding ->
        NavDisplay(
            backStack = navigation.mainBackStack,
            onBack = navigation::goBack,
            modifier = Modifier.padding(innerPadding),
            entryProvider = entryProvider {
                entryProviderInstallers.forEach { installer ->
                    installer()
                }
            },
        )
    }
}