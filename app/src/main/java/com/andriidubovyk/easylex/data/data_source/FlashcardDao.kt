package com.andriidubovyk.easylex.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andriidubovyk.easylex.domain.model.Flashcard
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {

    @Query("SELECT * FROM flashcard")
    fun getFlashcards(): Flow<List<Flashcard>>

    @Query("SELECT * FROM flashcard WHERE id = :id")
    suspend fun getFlashcardById(id: Int): Flashcard?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcard(flashcard: Flashcard)

    @Delete
    suspend fun deleteFlashcard(flashcard: Flashcard)

    @Query("DELETE FROM flashcard")
    suspend fun deleteAllFlashcards()

    @Query("SELECT * FROM flashcard ORDER BY score ASC LIMIT :limit")
    suspend fun getLowestScoreFlashcards(limit: Int): List<Flashcard>

    @Query("SELECT CASE WHEN COUNT(*) > 0 THEN SUM(score) ELSE 0 END FROM flashcard")
    suspend fun getTotalScore(): Int
}