package com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard.view_model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.model.InvalidFlashcardException
import com.andriidubovyk.easylex.domain.use_case.FlashcardUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditFlashcardViewModel @Inject constructor(
    private val flashcardUseCases: FlashcardUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(AddEditFlashcardState())
    val state = _state.asStateFlow()

    init {
        savedStateHandle.get<Int>("flashcardId")?.let {
            initFlashcard(it)
        }
    }

    fun onEvent(event: AddEditFlashcardEvent) {
        when (event) {
            is AddEditFlashcardEvent.UpdateWord -> processUpdateWord(event.word)
            is AddEditFlashcardEvent.UpdateDefinition -> processUpdateDefinition(event.definition)
            is AddEditFlashcardEvent.UpdateTranslation -> processUpdateTranslation(event.translation)
            is AddEditFlashcardEvent.SaveFlashcard -> processSaveFlashcard()
        }
    }

    private fun processUpdateWord(word: String) = viewModelScope.launch {
        _state.update {
            it.copy(word = word)
        }
    }

    private fun processUpdateDefinition(definition: String) = viewModelScope.launch {
        _state.update {
            it.copy(definition = definition)
        }
    }

    private fun processUpdateTranslation(translation: String) = viewModelScope.launch {
        _state.update {
            it.copy(translation = translation)
        }
    }

    private fun processSaveFlashcard() {
        viewModelScope.launch {
            try {
                flashcardUseCases.addFlashcard(
                    Flashcard(
                        id = state.value.currentFlashcardId,
                        word = state.value.word,
                        definition = state.value.definition.takeIf { it.isNotBlank() },
                        translation = state.value.translation.takeIf { it.isNotBlank() },
                        timestamp = System.currentTimeMillis(),
                        score = if (state.value.currentFlashcardId == -1) {
                            0
                        } else {
                            flashcardUseCases.getFlashcard(
                                state.value.currentFlashcardId ?: -1
                            )?.score ?: 0
                        }
                    )
                )
                _state.update { it.copy(isFlashcardSaved = true) }
            } catch (e: InvalidFlashcardException) {
                _state.update {
                    it.copy(
                        snackbarMessage = e.message ?: "Couldn't save this flashcard"
                    )
                }
            }
        }
    }

    private fun initFlashcard(id: Int) = viewModelScope.launch {
        if (id == -1) return@launch
        flashcardUseCases.getFlashcard(id)?.let { flashcard ->
            _state.update {
                it.copy(
                    currentFlashcardId = flashcard.id,
                    word = flashcard.word,
                    definition = flashcard.definition ?: "",
                    translation = flashcard.translation ?: ""
                )
            }
        }
    }
}