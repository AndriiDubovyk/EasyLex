package com.andriidubovyk.easylex.presentation.screen.settings.view_model

import androidx.lifecycle.ViewModel
import com.andriidubovyk.easylex.domain.model.Time
import com.andriidubovyk.easylex.domain.use_case.notification.NotificationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val notificationUseCases: NotificationUseCases
) : ViewModel() {


    private val _state = MutableStateFlow(
        SettingsState(
            notificationsEnabled = notificationUseCases.isNotificationsEnabled(),
            notificationsTime = notificationUseCases.getNotificationsTime()
        )
    )
    val state = _state.asStateFlow()


    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SetNotificationsEnabled -> processSetNotificationsEnabled(event.value)
            is SettingsEvent.SetNotificationsTime -> processSetNotificationsTime(event.time)
        }
    }

    private fun processSetNotificationsEnabled(value: Boolean) {
        _state.update {
            it.copy(notificationsEnabled = value)
        }
        if (value) notificationUseCases.scheduleNotifications(state.value.notificationsTime)
        else notificationUseCases.cancelNotifications()
    }

    private fun processSetNotificationsTime(time: Time) {
        _state.update {
            it.copy(notificationsTime = time)
        }
        notificationUseCases.scheduleNotifications(state.value.notificationsTime)
    }


}