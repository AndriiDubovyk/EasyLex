package com.andriidubovyk.easylex.presentation.component.bottom_nav_bar

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationPanel(navController: NavController) {
    val items = listOf(
        BottomNavigationItem.Flashcards,
        BottomNavigationItem.Study,
        BottomNavigationItem.Account,
        BottomNavigationItem.Settings
    )
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomAppBar {
        items.forEach {
            NavigationBarItem(
                selected = it.screenRoute == backStackEntry.value?.destination?.route,
                onClick = {
                    navController.navigate(it.screenRoute) {
                        launchSingleTop = true // Don't navigate if we are already at this tab
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = stringResource(it.titleRes)
                    )
                }
            )
        }
    }
}