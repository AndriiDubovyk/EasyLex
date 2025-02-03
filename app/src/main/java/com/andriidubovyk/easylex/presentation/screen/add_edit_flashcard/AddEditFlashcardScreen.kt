package com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.presentation.component.FlashcardFieldWithLabel
import com.andriidubovyk.easylex.presentation.component.SelectDefinitionDialog
import com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard.view_model.AddEditFlashcardEvent
import com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard.view_model.AddEditFlashcardViewModel
import kotlinx.coroutines.delay

@Composable
fun AddEditFlashcardScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddEditFlashcardViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(state.isFlashcardSaved) {
        if (state.isFlashcardSaved) navController.navigateUp()
    }

    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { snackbarHostState.showSnackbar(it) }
        delay(1000) // Prevent spam with same message
        viewModel.onEvent(AddEditFlashcardEvent.OnResetSnackbar)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditFlashcardEvent.SaveFlashcard)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save_flashcard)
                )
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
        ) {
            val fieldsPadding = 25.dp
            item {
                FlashcardFieldWithLabel(
                    modifier = Modifier.padding(fieldsPadding),
                    label = stringResource(R.string.word),
                    value = state.word,
                    onValueChange = { viewModel.onEvent(AddEditFlashcardEvent.UpdateWord(it)) },
                )
                HorizontalDivider()
                FlashcardFieldWithLabel(
                    modifier = Modifier
                        .padding(
                            start = fieldsPadding,
                            top = fieldsPadding,
                            end = fieldsPadding,
                            bottom = 5.dp
                        ),
                    label = stringResource(R.string.definition),
                    value = state.definition,
                    onValueChange = { viewModel.onEvent(AddEditFlashcardEvent.UpdateDefinition(it)) },
                    lines = 3,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(fieldsPadding),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            viewModel.onEvent(AddEditFlashcardEvent.GetDefinitionsFromDictionary)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.dictionary),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
                HorizontalDivider()
                FlashcardFieldWithLabel(
                    modifier = Modifier.padding(fieldsPadding),
                    label = stringResource(R.string.translation),
                    value = state.translation,
                    onValueChange = { viewModel.onEvent(AddEditFlashcardEvent.UpdateTranslation(it)) },
                )
            }
        }
    }

    state.dialogDefinitions?.let { definitions ->
        SelectDefinitionDialog(
            definitions = definitions,
            onSelect = {
                viewModel.onEvent(AddEditFlashcardEvent.SelectDefinitionFromDialog(it))
            },
            onCancel = {
                viewModel.onEvent(AddEditFlashcardEvent.CloseDefinitionsDialog)
            }
        )
    }
}
