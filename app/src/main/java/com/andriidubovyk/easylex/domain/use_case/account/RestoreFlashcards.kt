package com.andriidubovyk.easylex.domain.use_case.account

import com.andriidubovyk.easylex.domain.repository.AccountRepository
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class RestoreFlashcards(
    private val accountRepository: AccountRepository,
    private val flashcardRepository: FlashcardRepository
) {

    suspend operator fun invoke() {
        val cloudFlashcards = accountRepository.getFlashcardsFromCloud()
        if (cloudFlashcards.isEmpty()) return
        flashcardRepository.deleteAllFlashcards()
        cloudFlashcards.forEach {
            flashcardRepository.insertFlashcard(it)
        }
    }
}