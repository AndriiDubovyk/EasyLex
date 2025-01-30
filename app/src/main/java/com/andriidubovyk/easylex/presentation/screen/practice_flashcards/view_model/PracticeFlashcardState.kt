package com.andriidubovyk.easylex.presentation.screen.practice_flashcards.view_model

import com.andriidubovyk.easylex.domain.model.Flashcard

data class PracticeFlashcardState(
    val flashcard: Flashcard? = null,
    val isAnswerVisible: Boolean = false,
    val userAnswerRating: UserAnswerRating = UserAnswerRating.NONE,
    val isWordPronounced: Boolean = false,
    val isGoingToNextFlashcard: Boolean = false
)

enum class UserAnswerRating {
    NONE, BAD, OK, GOOD
}
