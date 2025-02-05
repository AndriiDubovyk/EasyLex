package com.andriidubovyk.easylex.presentation.screen.account

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.andriidubovyk.easylex.presentation.component.account_view.AccountViewProfile
import com.andriidubovyk.easylex.presentation.component.account_view.AccountViewSignIn
import com.andriidubovyk.easylex.presentation.screen.account.utiils.getBeginSignInRequest
import com.andriidubovyk.easylex.presentation.screen.account.view_model.AccountEvent
import com.andriidubovyk.easylex.presentation.screen.account.view_model.AccountState
import com.andriidubovyk.easylex.presentation.screen.account.view_model.AccountViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Suppress("DEPRECATION")
@Composable
fun AccountScreen(modifier: Modifier = Modifier, viewModel: AccountViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val oneTapClient = Identity.getSignInClient(context)

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            scope.launch {
                if (result.resultCode == RESULT_OK) {
                    val credential =
                        oneTapClient.getSignInCredentialFromIntent(result.data ?: return@launch)
                    val googleIdToken = credential.googleIdToken
                    viewModel.onEvent(AccountEvent.SignInWithGoogleIdToken(googleIdToken))
                }
                viewModel.onEvent(AccountEvent.ResetSignInClick)
            }
        }
    )

    when (state) {
        is AccountState.SignIn -> {
            val signInState = state as AccountState.SignIn
            LaunchedEffect(signInState.isSignInClicked) {
                if (signInState.isSignInClicked) {
                    val signInRequest = context.getBeginSignInRequest()
                    val result = try {
                        oneTapClient.beginSignIn(signInRequest).await()
                    } catch (e: Exception) {
                        null
                    }
                    result?.pendingIntent?.intentSender?.let {
                        launcher.launch(IntentSenderRequest.Builder(it).build())
                    }
                }
            }
            AccountViewSignIn(
                modifier = modifier,
                onSignInClick = { viewModel.onEvent(AccountEvent.SignInClick) }
            )
        }

        is AccountState.Profile -> {
            val profileState = state as AccountState.Profile
            AccountViewProfile(
                modifier = modifier,
                onSignOut = { viewModel.onEvent(AccountEvent.SignOut) },
                onBackupFlashcards = { viewModel.onEvent(AccountEvent.BackupFlashcardsToCloud) },
                onRestoreFlashcards = { viewModel.onEvent(AccountEvent.RestoreFlashcardsFromCloud) },
                onResetSnackbar = { viewModel.onEvent(AccountEvent.OnResetSnackbar) },
                snackbarMessage = profileState.snackbarMessage,
                userData = profileState.userData
            )
        }
    }
}
