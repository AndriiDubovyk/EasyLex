package com.andriidubovyk.easylex.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flashcard(
    @PrimaryKey val id: Int? = null,
    val word: String = "",
    val definition: String? = null,
    val translation: String? = null,
    val score: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)