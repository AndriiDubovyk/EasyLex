package com.andriidubovyk.easylex.domain.use_case.account

data class AccountUseCases(
    val signOut: SignOut,
    val getCurrentUserData: GetCurrentUserData,
    val signIn: SignIn,
    val backupFlashcards: BackupFlashcards,
    val restoreFlashcards: RestoreFlashcards
)