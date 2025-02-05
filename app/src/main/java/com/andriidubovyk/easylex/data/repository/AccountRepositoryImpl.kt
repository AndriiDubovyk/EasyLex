package com.andriidubovyk.easylex.data.repository

import android.content.Context
import com.andriidubovyk.easylex.domain.model.BackupResult
import com.andriidubovyk.easylex.domain.model.Flashcard
import com.andriidubovyk.easylex.domain.model.RestoreBackupResult
import com.andriidubovyk.easylex.domain.model.SignInResult
import com.andriidubovyk.easylex.domain.model.UserData
import com.andriidubovyk.easylex.domain.repository.AccountRepository
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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

    override suspend fun getFlashcardsFromCloud(): RestoreBackupResult {
        val userData = getCurrentUserData()
        var message = "Unknown error"
        var isSuccess = false
        val flashcardList = mutableListOf<Flashcard>()
        userData?.let { ud ->
            val flashcardsRef = getUserFlashcardCollectionRef(ud.userId)
            flashcardsRef.get()
                .addOnSuccessListener {
                    if (it.isEmpty) {
                        message = "There no flashcards in the cloud"
                        isSuccess = false
                        return@addOnSuccessListener
                    }
                    for (document in it) {
                        @Suppress("DEPRECATION")
                        val flashcard = document.toObject<Flashcard>()
                        flashcardList.add(flashcard)
                    }
                    if (flashcardList.isNotEmpty()) {
                        message = "Successfully restored ${flashcardList.size} flashcards"
                        isSuccess = true
                    }
                }
                .addOnFailureListener {
                    message = "Can't restore flashcards"
                    isSuccess = false
                }
                .await()
        }
        return RestoreBackupResult(
            isSuccess = isSuccess,
            message = message,
            flashcardList = flashcardList
        )
    }

    override suspend fun setCloudFlashcards(flashcards: List<Flashcard>): BackupResult =
        withContext(Dispatchers.IO) {
            val userData = getCurrentUserData()
            var successCount = 0
            var errorCount = 0
            userData?.let { ud ->
                val flashcardsRef = getUserFlashcardCollectionRef(ud.userId)
                flashcardsRef.clearCollection()
                for (flashcard in flashcards) {
                    flashcardsRef.document(flashcard.id.toString()).set(flashcard)
                        .addOnSuccessListener { successCount++ }
                        .addOnFailureListener { errorCount++ }.await()
                }
            }
            return@withContext BackupResult(
                isSuccess = true,
                message = "Successfully saved: $successCount, errors: $errorCount"
            )
        }


    private suspend fun CollectionReference.clearCollection() {
        this.get().addOnSuccessListener { querySnapshot ->
            val batch = Firebase.firestore.batch()
            querySnapshot.documents.forEach { document ->
                batch.delete(document.reference)
            }
            batch.commit()
        }.await()
    }

    private fun getUserFlashcardCollectionRef(userId: String): CollectionReference {
        return Firebase.firestore
            .collection("users").document(userId)
            .collection("flashcards")
    }
}