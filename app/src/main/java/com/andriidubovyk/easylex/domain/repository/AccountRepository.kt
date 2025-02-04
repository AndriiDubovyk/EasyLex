package com.andriidubovyk.easylex.domain.repository

import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.model.SignInResult
import com.andriidubovyk.easylex.domain.model.UserData

interface AccountRepository {
    suspend fun signOut()
    suspend fun signIn(token: String?): SignInResult
    suspend fun getCurrentUserData(): UserData?
    suspend fun getFlashcardsFromCloud(): List<Flashcard>
    suspend fun setCloudFlashcards(flashcards: List<Flashcard>)
}