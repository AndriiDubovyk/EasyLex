package com.andriidubovyk.easylex.domain.repository

import com.andriidubovyk.easylex.domain.model.Flashcard
import kotlinx.coroutines.flow.Flow

interface FlashcardRepository {
    fun getFlashcards(): Flow<List<Flashcard>>

    suspend fun getFlashcardById(id: Int): Flashcard?

    suspend fun insertFlashcard(flashcard: Flashcard)

    suspend fun deleteFlashcard(flashcard: Flashcard)

    suspend fun deleteAllFlashcards()

    suspend fun getLowestScoreFlashcards(limit: Int): List<Flashcard>

    suspend fun getTotalScore(): Int
}