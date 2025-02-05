package com.andriidubovyk.easylex.domain.repository

import com.andriidubovyk.easylex.domain.model.BackupResult
import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.model.RestoreBackupResult
import com.andriidubovyk.easylex.domain.model.SignInResult
import com.andriidubovyk.easylex.domain.model.UserData

interface AccountRepository {
    suspend fun signOut()
    suspend fun signIn(token: String?): SignInResult
    suspend fun getCurrentUserData(): UserData?
    suspend fun getFlashcardsFromCloud(): RestoreBackupResult
    suspend fun setCloudFlashcards(flashcards: List<Flashcard>): BackupResult
}