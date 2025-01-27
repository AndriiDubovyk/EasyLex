package com.andriidubovyk.easylex.data.repository

import com.andriidubovyk.easylex.data.data_source.FlashcardDao
import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.repository.FlashcardRepository
import kotlinx.coroutines.flow.Flow

class FlashcardRepositoryImpl(
    private val dao: FlashcardDao
) : FlashcardRepository {

    override fun getFlashcards(): Flow<List<Flashcard>> {
        return dao.getFlashcards()
    }

    override suspend fun getFlashcardById(id: Int): Flashcard? {
        return dao.getFlashcardById(id)
    }

    override suspend fun insertFlashcard(flashcard: Flashcard) {
        dao.insertFlashcard(flashcard)
    }

    override suspend fun deleteFlashcard(flashcard: Flashcard) {
        dao.deleteFlashcard(flashcard)
    }

    override suspend fun deleteAllFlashcards() {
        dao.deleteAllFlashcards()
    }

    override suspend fun getLowestScoreFlashcards(limit: Int): List<Flashcard> {
        return dao.getLowestScoreFlashcards(limit)
    }

    override suspend fun getTotalScore(): Int {
        return dao.getTotalScore()
    }
}