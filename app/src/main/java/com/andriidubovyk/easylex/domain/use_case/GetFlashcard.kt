package com.andriidubovyk.easylex.domain.use_case

import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class GetFlashcard(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(id: Int): Flashcard? {
        return repository.getFlashcardById(id)
    }
}