package com.andriidubovyk.easylex.domain.use_case

import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class GetLowestScoreFlashcards(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(limit: Int = 5): List<Flashcard> {
        return repository.getLowestScoreFlashcards(limit)
    }
}