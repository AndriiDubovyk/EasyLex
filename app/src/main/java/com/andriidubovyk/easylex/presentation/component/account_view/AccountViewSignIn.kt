package com.andriidubovyk.easylex.presentation.component.account_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andriidubovyk.easylex.R

@Composable
fun AccountViewSignIn(
    modifier: Modifier,
    onSignInClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onSignInClick) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}