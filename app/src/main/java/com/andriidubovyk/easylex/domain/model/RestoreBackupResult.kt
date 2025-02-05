package com.andriidubovyk.easylex.domain.model

data class RestoreBackupResult(
    val isSuccess: Boolean,
    val flashcardList: List<Flashcard>,
    val message: String
)
