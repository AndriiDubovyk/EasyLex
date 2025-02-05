package com.andriidubovyk.easylex.presentation.screen.account.view_model

sealed interface AccountEvent {
    data object SignOut : AccountEvent
    data object SignInClick : AccountEvent
    data object ResetSignInClick : AccountEvent
    data class SignInWithGoogleIdToken(val googleIdToken: String?) : AccountEvent
    data object BackupFlashcardsToCloud : AccountEvent
    data object RestoreFlashcardsFromCloud : AccountEvent
    data object OnResetSnackbar : AccountEvent
}