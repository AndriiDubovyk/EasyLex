package com.andriidubovyk.easylex.domain.use_case

data class FlashcardUseCases(
    val getFlashcards: GetFlashcards,
    val getFlashcard: GetFlashcard,
    val addFlashcard: AddFlashcard,
    val deleteFlashcard: DeleteFlashcard,
    val getTotalScore: GetTotalScore,
    val getLowestScoreFlashcards: GetLowestScoreFlashcards
)
