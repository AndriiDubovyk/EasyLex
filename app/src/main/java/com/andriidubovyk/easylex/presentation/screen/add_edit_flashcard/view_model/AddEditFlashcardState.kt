package com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard.view_model

data class AddEditFlashcardState(
    val word: String = "",
    val definition: String = "",
    val translation: String = "",
    val currentFlashcardId: Int? = null,
    val isFlashcardSaved: Boolean = false,
    val snackbarMessage: String? = null
)
