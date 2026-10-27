package com.bc.bookcritic.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bc.book.ui.library.AddBookRouter
import com.bc.book.ui.home.HomeRouter
import com.bc.bookcritic.nav.bottom_nav.AppDestination
import com.bc.bookcritic.nav.bottom_nav.BottomBarNavigation

@Composable
fun BookCriticApp() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarRoutes = AppDestination.bottomBarItems.map { it.route }

    val showBottomBar = currentRoute in bottomBarRoutes

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBarNavigation(
                    selectedRoute = currentRoute,
                    onDestinationClick = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        },
        floatingActionButton = {
            if (showBottomBar) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(AppDestination.AddBook.route)
                    },
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Add book",
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(AppDestination.Home.route) {
                HomeRouter()
            }

            composable(AppDestination.Library.route) {
//                LibraryRoute()
            }

            composable(AppDestination.Stats.route) {
//                StatsRoute()
            }

            composable(AppDestination.AddBook.route) {
                AddBookRouter()
            }
        }
    }
}