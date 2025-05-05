package com.andriidubovyk.easylex.domain.use_case.account

import com.andriidubovyk.easylex.domain.repository.AccountRepository
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.first

class BackupFlashcards(
    private val accountRepository: AccountRepository,
    private val flashcardRepository: FlashcardRepository
) {

    suspend operator fun invoke() {
        val localFlashcards = flashcardRepository.getFlashcards().first()
        if (localFlashcards.isEmpty()) return
        accountRepository.setCloudFlashcards(localFlashcards)
    }
}