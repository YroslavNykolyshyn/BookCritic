package com.bc.bookcritic.nav.bottom_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class AppDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
) {
    data object Home : AppDestination(
        route = "home",
        label = "Home",
        icon = Icons.Outlined.Home,
    )

    data object Library : AppDestination(
        route = "library",
        label = "Library",
        icon = Icons.AutoMirrored.Outlined.MenuBook,
    )

    data object Stats : AppDestination(
        route = "stats",
        label = "Stats",
        icon = Icons.Outlined.BarChart,
    )

    data object AddBook : AppDestination(
        route = "add_book",
        label = "Add Book",
        icon = Icons.Outlined.Add,
    )

    companion object {
        val bottomBarItems = listOf(
            Home,
            Library,
            Stats,
        )
    }
}