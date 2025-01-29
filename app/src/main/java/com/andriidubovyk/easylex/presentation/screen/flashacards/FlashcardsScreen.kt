package com.andriidubovyk.easylex.presentation.screen.flashacards

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.presentation.component.FlashcardComponent
import com.andriidubovyk.easylex.presentation.component.FlashcardSearchBar
import com.andriidubovyk.easylex.presentation.component.OrderSection
import com.andriidubovyk.easylex.presentation.navigation.Screen
import com.andriidubovyk.easylex.presentation.screen.flashacards.view_model.FlashcardsEvent
import com.andriidubovyk.easylex.presentation.screen.flashacards.view_model.FlashcardsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // padding already used in previous scaffold
@Composable
fun FlashcardsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: FlashcardsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.ADD_EDIT_FLASHCARDS.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_flashcard)
                )
            }
        }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlashcardSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = state.searchText,
                    onValueChange = { text ->
                        viewModel.onEvent(
                            FlashcardsEvent.ChangeSearchText(text)
                        )
                    },
                    onReset = { viewModel.onEvent(FlashcardsEvent.ResetSearch) }
                )
                IconButton(
                    onClick = { viewModel.onEvent(FlashcardsEvent.ToggleOrderSection) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Sort,
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary),
                    flashcardOrder = state.flashcardOrder,
                    onOrderChange = { order -> viewModel.onEvent(FlashcardsEvent.Order(order)) }
                )
            }
            HorizontalDivider()
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(horizontal = 20.dp),
                verticalArrangement = if (state.isLoading) Arrangement.Center else Arrangement.spacedBy(
                    20.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.isLoading) {
                    item {
                        CircularProgressIndicator()
                    }
                } else {
                    items(state.flashcards) { flashcard ->
                        FlashcardComponent(
                            flashcard = flashcard,
                            onClick = {
                                navController.navigate("${Screen.ADD_EDIT_FLASHCARDS.route}?flashcardId=${flashcard.id}")
                            },
                            onDeleteClick = {
                                viewModel.onEvent(FlashcardsEvent.DeleteFlashcard(flashcard))
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = context.getString((R.string.flashcard_deleted)),
                                        actionLabel = context.getString((R.string.undo))
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(FlashcardsEvent.RestoreFlashcard)
                                    }
                                }
                            }
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(50.dp)) }
            }
        }
    }
}

