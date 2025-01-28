package com.andriidubovyk.easylex.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard.AddEditFlashcardScreen
import com.andriidubovyk.easylex.presentation.screen.flashacards.FlashcardsScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    startDestination: String = Screen.FLASHCARDS.route
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.FLASHCARDS.route) {
            FlashcardsScreen(Modifier.fillMaxSize(), navController = navController)
        }
        composable(
            route = Screen.ADD_EDIT_FLASHCARDS.route +
                    "?flashcardId={flashcardId}",
            arguments = listOf(
                navArgument(
                    name = "flashcardId"
                ) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditFlashcardScreen(Modifier.fillMaxSize(), navController = navController)
        }
    }
}