package com.andriidubovyk.easylex.presentation.screen.account.view_model

import com.andriidubovyk.easylex.domain.model.UserData

sealed interface AccountState {
    data class SignIn(
        val isSignInSuccessful: Boolean = false,
        val signInError: String? = null,
        val isSignInClicked: Boolean = false
    ) : AccountState

    data class Profile(val userData: UserData? = null, val snackbarMessage: String? = null) :
        AccountState
}