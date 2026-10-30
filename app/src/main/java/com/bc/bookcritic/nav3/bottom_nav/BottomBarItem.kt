package com.bc.bookcritic.nav3.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.bc.core.navigation.Home
import com.bc.core.navigation.Library
import com.bc.core.navigation.Stats

sealed class BottomBarItem(
    val key: NavKey,
    val label: String,
    val icon: ImageVector,
) {
    data object HomeBottomBarItem : BottomBarItem(
        key = Home,
        label = "Home",
        icon = Icons.Outlined.Home,
    )

    data object LibraryBottomBarItem : BottomBarItem(
        key = Library,
        label = "Library",
        icon = Icons.AutoMirrored.Outlined.MenuBook,
    )

    data object StatsBottomBarItem : BottomBarItem(
        key = Stats,
        label = "Stats",
        icon = Icons.Outlined.BarChart,
    )

    companion object {
        val bottomBarItems = listOf(
            HomeBottomBarItem,
            LibraryBottomBarItem,
            StatsBottomBarItem,
        )
    }
}