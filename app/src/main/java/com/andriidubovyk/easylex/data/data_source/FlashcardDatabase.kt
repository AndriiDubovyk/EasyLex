package com.andriidubovyk.easylex.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andriidubovyk.easylex.domain.model.Flashcard

@Database(
    entities = [Flashcard::class],
    version = 1
)
abstract class FlashcardDatabase : RoomDatabase() {

    abstract val flashcardDao: FlashcardDao

    companion object {
        const val DATABASE_NAME = "flashcards_db"
    }
}