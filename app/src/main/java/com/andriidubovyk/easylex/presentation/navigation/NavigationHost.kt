package com.andriidubovyk.easylex.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.andriidubovyk.easylex.presentation.screen.account.AccountScreen
import com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard.AddEditFlashcardScreen
import com.andriidubovyk.easylex.presentation.screen.flashacards.FlashcardsScreen
import com.andriidubovyk.easylex.presentation.screen.practice_flashcards.PracticeFlashcardScreen
import com.andriidubovyk.easylex.presentation.screen.settings.SettingsScreen
import com.andriidubovyk.easylex.presentation.screen.study.StudyScreen

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.FLASHCARDS.route
) {
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
        composable(route = Screen.STUDY.route) {
            StudyScreen(Modifier.fillMaxSize(), navController)
        }
        composable(
            route = Screen.PRACTICE_FLASHCARD.route +
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
            PracticeFlashcardScreen(Modifier.fillMaxSize(), navController = navController)
        }
        composable(route = Screen.ACCOUNT.route) {
            AccountScreen(Modifier.fillMaxSize())
        }
        composable(route = Screen.SETTINGS.route) {
            SettingsScreen(Modifier.fillMaxSize())
        }
    }
}