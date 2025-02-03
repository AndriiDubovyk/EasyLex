package com.andriidubovyk.easylex.presentation.screen.add_edit_flashcard.view_model

sealed interface AddEditFlashcardEvent {
    data class UpdateWord(val word: String) : AddEditFlashcardEvent
    data class UpdateDefinition(val definition: String) : AddEditFlashcardEvent
    data class UpdateTranslation(val translation: String) : AddEditFlashcardEvent
    data object SaveFlashcard : AddEditFlashcardEvent
    data object GetDefinitionsFromDictionary : AddEditFlashcardEvent
    data class SelectDefinitionFromDialog(val value: String) : AddEditFlashcardEvent
    data object CloseDefinitionsDialog : AddEditFlashcardEvent
    data object OnResetSnackbar : AddEditFlashcardEvent
}