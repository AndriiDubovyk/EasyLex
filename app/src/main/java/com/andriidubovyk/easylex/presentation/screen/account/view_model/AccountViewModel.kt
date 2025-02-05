package com.andriidubovyk.easylex.presentation.screen.account.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriidubovyk.easylex.domain.use_case.account.AccountUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val useCases: AccountUseCases
) : ViewModel() {
    private val _state = MutableStateFlow<AccountState>(AccountState.SignIn())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val currentUserData = useCases.getCurrentUserData()
            if (currentUserData != null) {
                _state.update {
                    AccountState.Profile(userData = currentUserData)
                }
            }
        }
    }

    fun onEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.SignOut -> processSignOut()
            is AccountEvent.SignInClick -> processSignInClick()
            is AccountEvent.BackupFlashcardsToCloud -> processBackupFlashcardsToCloud()
            is AccountEvent.RestoreFlashcardsFromCloud -> processRestoreFlashcardsFromCloud()
            is AccountEvent.SignInWithGoogleIdToken -> processSignInWithGoogleIdToken(event.googleIdToken)
            is AccountEvent.ResetSignInClick -> processResetSignInClick()
            is AccountEvent.OnResetSnackbar -> processOnResetSnackbar()
        }
    }

    private fun processSignOut() = viewModelScope.launch {
        useCases.signOut()
        _state.update { AccountState.SignIn() }
    }

    private fun processSignInWithGoogleIdToken(googleIdToken: String?) = viewModelScope.launch {
        val signInResult = useCases.signIn(googleIdToken)
        if (signInResult.data != null) {
            _state.update { AccountState.Profile(signInResult.data) }
        } else {
            _state.update { AccountState.SignIn(signInError = signInResult.errorMessage) }
        }
    }

    private fun processSignInClick() = viewModelScope.launch {
        if (state.value !is AccountState.SignIn) return@launch
        _state.update {
            val signInState = it as AccountState.SignIn
            signInState.copy(isSignInClicked = true)
        }
    }

    private fun processResetSignInClick() = viewModelScope.launch {
        if (state.value !is AccountState.SignIn) return@launch
        _state.update {
            val signInState = it as AccountState.SignIn
            signInState.copy(isSignInClicked = false)
        }
    }

    private fun processBackupFlashcardsToCloud() = viewModelScope.launch {
        val backupResult = useCases.backupFlashcards()
        if (state.value !is AccountState.Profile) return@launch
        _state.update {
            val profileState = it as AccountState.Profile
            profileState.copy(snackbarMessage = backupResult.message)
        }
    }

    private fun processRestoreFlashcardsFromCloud() = viewModelScope.launch {
        val restoreBackupResult = useCases.restoreFlashcards()
        if (state.value !is AccountState.Profile) return@launch
        _state.update {
            val profileState = it as AccountState.Profile
            profileState.copy(snackbarMessage = restoreBackupResult.message)
        }
    }

    private fun processOnResetSnackbar() = viewModelScope.launch {
        if (state.value !is AccountState.Profile) return@launch
        _state.update {
            val profileState = it as AccountState.Profile
            profileState.copy(snackbarMessage = null)
        }
    }
}