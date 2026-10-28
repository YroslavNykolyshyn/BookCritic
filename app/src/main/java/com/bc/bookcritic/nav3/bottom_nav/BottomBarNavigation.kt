package com.bc.bookcritic.nav3.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavKey
import com.bc.core.theme.AppTheme.colors

@Composable
fun BottomBarNavigation(
    selectedDestination: NavKey?,
    onDestinationClick: (NavKey) -> Unit,
) {
    NavigationBar(
        containerColor = colors.bottomBarBackground,
        contentColor = colors.secondary,
        windowInsets = NavigationBarDefaults.windowInsets,
    ) {
        BottomBarItem.bottomBarItems.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.key,
                onClick = {
                    onDestinationClick(destination.key)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.label,
                    )
                },
                label = {
                    Text(destination.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.primary,
                    unselectedIconColor = colors.secondary,
                    selectedTextColor = colors.primary,
                    unselectedTextColor = colors.secondary,
                    indicatorColor = colors.surfaceVariant,
                ),
            )
        }
    }
}