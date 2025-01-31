package com.andriidubovyk.easylex.presentation.screen.settings

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.domain.model.Time
import com.andriidubovyk.easylex.presentation.component.SettingsRow
import com.andriidubovyk.easylex.presentation.component.TimeDisplay
import com.andriidubovyk.easylex.presentation.screen.settings.view_model.SettingsEvent
import com.andriidubovyk.easylex.presentation.screen.settings.view_model.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    var isNotificationTimeDialogOpened by remember { mutableStateOf(false) }

    val requestNotificationLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.onEvent(SettingsEvent.SetNotificationsEnabled(true))
            }
        }

    Column(modifier = modifier) {
        SettingsRow {
            Text(
                text = stringResource(R.string.enable_notifications),
                style = MaterialTheme.typography.titleMedium
            )
            Switch(
                checked = state.notificationsEnabled,
                onCheckedChange = {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        viewModel.onEvent(SettingsEvent.SetNotificationsEnabled(it))
                    } else {
                        if (it) requestNotificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        else viewModel.onEvent(SettingsEvent.SetNotificationsEnabled(false))
                    }
                }
            )
        }
        HorizontalDivider()
        SettingsRow {
            Text(
                text = stringResource(R.string.notifications_time),
                style = MaterialTheme.typography.titleMedium
            )
            TimeDisplay(
                time = state.notificationsTime,
                onClick = { isNotificationTimeDialogOpened = true }
            )
        }
        HorizontalDivider()
    }

    if (isNotificationTimeDialogOpened) {
        BasicAlertDialog(onDismissRequest = { isNotificationTimeDialogOpened = false }) {
            val timePickerState by remember {
                mutableStateOf(
                    TimePickerState(
                        initialHour = state.notificationsTime.hour,
                        initialMinute = state.notificationsTime.minute,
                        is24Hour = true
                    )
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TimePicker(state = timePickerState)
                Button(onClick = {
                    viewModel.onEvent(
                        SettingsEvent.SetNotificationsTime(
                            Time(timePickerState.hour, timePickerState.minute)
                        )
                    )
                    isNotificationTimeDialogOpened = false
                }) {
                    Text(
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}