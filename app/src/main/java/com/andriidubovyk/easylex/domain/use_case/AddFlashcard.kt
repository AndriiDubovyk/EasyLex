package com.andriidubovyk.easylex.domain.use_case

import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.model.InvalidFlashcardException
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class AddFlashcard(
    private val repository: FlashcardRepository
) {

    @Throws(InvalidFlashcardException::class)
    suspend operator fun invoke(flashcard: Flashcard) {
        if (flashcard.word.isBlank()) {
            throw InvalidFlashcardException("The word of the flashcard can't be empty.")
        }
        if (flashcard.definition.isNullOrBlank() && flashcard.translation.isNullOrBlank()) {
            throw InvalidFlashcardException("The flashcard must have a translation or definition.")
        }
        repository.insertFlashcard(flashcard)
    }
}