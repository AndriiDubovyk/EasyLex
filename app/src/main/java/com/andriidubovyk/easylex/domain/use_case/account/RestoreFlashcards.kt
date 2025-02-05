package com.andriidubovyk.easylex.domain.use_case.account

import com.andriidubovyk.easylex.domain.model.RestoreBackupResult
import com.andriidubovyk.easylex.domain.repository.AccountRepository
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class RestoreFlashcards(
    private val accountRepository: AccountRepository,
    private val flashcardRepository: FlashcardRepository
) {

    suspend operator fun invoke(): RestoreBackupResult {
        val restoreBackupResult = accountRepository.getFlashcardsFromCloud()
        if (!restoreBackupResult.isSuccess) return restoreBackupResult
        restoreBackupResult.flashcardList.forEach {
            flashcardRepository.insertFlashcard(it)
        }
        return restoreBackupResult
    }
}