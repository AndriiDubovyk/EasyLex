package com.andriidubovyk.easylex.data.repository

import android.content.Context
import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.model.SignInResult
import com.andriidubovyk.easylex.domain.model.UserData
import com.andriidubovyk.easylex.domain.repository.AccountRepository
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class AccountRepositoryImpl(
    context: Context,
) : AccountRepository {
    private val auth = Firebase.auth
    private val oneTapClient = Identity.getSignInClient(context)

    override suspend fun signOut() {
        try {
            @Suppress("DEPRECATION")
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signIn(token: String?): SignInResult {
        val googleCredentials = GoogleAuthProvider.getCredential(token, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    override suspend fun getCurrentUserData(): UserData? {
        return auth.currentUser?.run {
            UserData(
                userId = uid,
                username = displayName,
                profilePictureUrl = photoUrl?.toString()
            )
        }
    }

    override suspend fun getFlashcardsFromCloud(): List<Flashcard> {
        val userId = getCurrentUserData()?.userId ?: return emptyList()
        val snapshot = Firebase.firestore
            .collection("users").document(userId)
            .collection("flashcards")
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(Flashcard::class.java) }
    }

    override suspend fun setCloudFlashcards(flashcards: List<Flashcard>) {
        val userId = getCurrentUserData()?.userId ?: return
        val collectionRef = Firebase.firestore
            .collection("users").document(userId)
            .collection("flashcards")

        // Delete old docs
        val oldDocs = collectionRef.get().await()
        val deleteBatch = Firebase.firestore.batch()
        oldDocs.forEach { deleteBatch.delete(it.reference) }
        deleteBatch.commit().await()

        // Write new docs
        val saveBatch = Firebase.firestore.batch()
        flashcards.forEach { flashcard ->
            val docRef = collectionRef.document(flashcard.id.toString())
            saveBatch.set(docRef, flashcard)
        }
        saveBatch.commit().await()
    }

}