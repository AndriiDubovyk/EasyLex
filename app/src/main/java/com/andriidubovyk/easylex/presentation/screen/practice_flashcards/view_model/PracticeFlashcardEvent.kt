package com.andriidubovyk.easylex.presentation.screen.practice_flashcards.view_model

sealed interface PracticeFlashcardEvent {
    data object ShowAnswer : PracticeFlashcardEvent
    data class SelectAnswerRating(val value: UserAnswerRating) : PracticeFlashcardEvent
    data object GoToNext : PracticeFlashcardEvent
    data object StopGoingToNext : PracticeFlashcardEvent
    data object StartPronouncingWord : PracticeFlashcardEvent
    data object StopPronouncingWord : PracticeFlashcardEvent
}