package com.andriidubovyk.easylex.presentation.screen.settings.view_model

import com.andriidubovyk.easylex.domain.model.Time

sealed interface SettingsEvent {
    data class SetNotificationsEnabled(val value: Boolean) : SettingsEvent
    data class SetNotificationsTime(val time: Time) : SettingsEvent
}