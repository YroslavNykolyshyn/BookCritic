package com.bc.bookcritic.nav.bottom_nav

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomBarNavigation(
    selectedRoute: String?,
    onDestinationClick: (String) -> Unit,
) {
    NavigationBar {
        AppDestination.bottomBarItems.forEach { destination ->
            NavigationBarItem(
                selected = selectedRoute == destination.route,
                onClick = {
                    onDestinationClick(destination.route)
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
            )
        }
    }
}