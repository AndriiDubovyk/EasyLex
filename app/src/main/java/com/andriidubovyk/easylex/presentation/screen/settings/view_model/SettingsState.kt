package com.andriidubovyk.easylex.presentation.screen.settings.view_model

import com.andriidubovyk.easylex.domain.model.Time

data class SettingsState(
    val notificationsEnabled: Boolean = false,
    val notificationsTime: Time = Time(21, 0)
)
