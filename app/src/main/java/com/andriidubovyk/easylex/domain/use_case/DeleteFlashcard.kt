package com.andriidubovyk.easylex.domain.use_case

import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class DeleteFlashcard(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(flashcard: Flashcard) {
        repository.deleteFlashcard(flashcard)
    }
}