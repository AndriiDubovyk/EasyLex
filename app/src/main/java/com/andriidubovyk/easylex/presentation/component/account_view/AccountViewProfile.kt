package com.andriidubovyk.easylex.presentation.component.account_view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.domain.model.UserData
import com.andriidubovyk.easylex.presentation.component.ConfirmDialog

@Composable
fun AccountViewProfile(
    modifier: Modifier,
    onSignOut: () -> Unit,
    onBackupFlashcards: () -> Unit,
    onRestoreFlashcards: () -> Unit,
    userData: UserData?,
) {
    var isBackupDataDialogOpened by remember { mutableStateOf(false) }
    var isRestoreDataDialogOpened by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (userData?.profilePictureUrl != null) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = stringResource(R.string.profile_picture),
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (userData?.username != null) {
                        Text(
                            text = userData.username,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                    Button(onClick = { onSignOut() }) {
                        Text(
                            text = stringResource(R.string.sign_out),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
        Column {
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isBackupDataDialogOpened = true }
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(
                    text = stringResource(R.string.backup_flashcards_to_the_cloud),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            HorizontalDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isRestoreDataDialogOpened = true }
                    .padding(horizontal = 20.dp, vertical = 15.dp)
            ) {
                Text(
                    text = stringResource(R.string.restore_flashcards_from_the_cloud),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }

    if (isBackupDataDialogOpened) {
        ConfirmDialog(
            text = stringResource(R.string.before_backup_message),
            onConfirm = {
                onBackupFlashcards()
                isBackupDataDialogOpened = false
            },
            onCancel = { isBackupDataDialogOpened = false }
        )
    }
    if (isRestoreDataDialogOpened) {
        ConfirmDialog(
            text = stringResource(R.string.before_restore_message),
            onConfirm = {
                onRestoreFlashcards()
                isRestoreDataDialogOpened = false
            },
            onCancel = { isRestoreDataDialogOpened = false }
        )
    }
}