package com.andriidubovyk.easylex.domain.repository

import com.andriidubovyk.easylex.domain.model.Time

interface NotificationRepository {
    fun scheduleNotifications(time: Time)
    fun cancelNotification()
    fun isNotificationsEnabled(): Boolean
    fun getNotificationsTime(): Time
}