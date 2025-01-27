package com.andriidubovyk.easylex.presentation.screen.flashacards.view_model

import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.utils.FlashcardOrder

sealed interface FlashcardsEvent {
    data class Order(val flashcardOrder: FlashcardOrder) : FlashcardsEvent
    data object ToggleOrderSection : FlashcardsEvent
    data class DeleteFlashcard(val flashcard: Flashcard) : FlashcardsEvent
    data class ChangeSearchText(val searchText: String) : FlashcardsEvent
    data object ResetSearch : FlashcardsEvent
    data object RestoreFlashcard : FlashcardsEvent
}