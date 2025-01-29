package com.andriidubovyk.easylex.presentation.component.bottom_nav_bar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.presentation.navigation.Screen

sealed class BottomNavigationItem(
    var titleRes: Int,
    var icon: ImageVector,
    var screenRoute: String
) {
    data object Flashcards : BottomNavigationItem(
        titleRes = R.string.flashcards,
        icon = Icons.Default.Dataset,
        screenRoute = Screen.FLASHCARDS.route
    )

    data object Study : BottomNavigationItem(
        titleRes = R.string.study,
        icon = Icons.Default.School,
        screenRoute = Screen.STUDY.route
    )

    data object Account : BottomNavigationItem(
        titleRes = R.string.account,
        icon = Icons.Default.Person,
        screenRoute = Screen.ACCOUNT.route
    )

    data object Settings : BottomNavigationItem(
        titleRes = R.string.settings,
        icon = Icons.Default.Settings,
        screenRoute = Screen.SETTINGS.route
    )
}