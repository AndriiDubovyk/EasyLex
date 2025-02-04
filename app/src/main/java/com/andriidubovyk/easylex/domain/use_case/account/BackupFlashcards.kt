package com.andriidubovyk.easylex.domain.use_case.account

import com.andriidubovyk.easylex.domain.repository.AccountRepository
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class BackupFlashcards(
    private val accountRepository: AccountRepository,
    private val flashcardRepository: FlashcardRepository
) {

    suspend operator fun invoke() {
        TODO("Not yet implemented")
    }
}