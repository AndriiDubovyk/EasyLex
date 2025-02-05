package com.andriidubovyk.easylex.presentation.screen.account.utiils

import android.content.Context
import com.andriidubovyk.easylex.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest

@Suppress("DEPRECATION")
fun Context.getBeginSignInRequest(): BeginSignInRequest {
    return BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(getString(R.string.web_client_id))
                .build()
        )
        .build()
}
