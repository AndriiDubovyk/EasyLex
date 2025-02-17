package com.andriidubovyk.easylex.domain.use_case.flashcard

import com.andriidubovyk.easylex.domain.repository.FlashcardRepository

class GetTotalScore(
    private val repository: FlashcardRepository
) {

    suspend operator fun invoke(): Int {
        return repository.getTotalScore()
    }
}