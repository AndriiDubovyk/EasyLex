package com.andriidubovyk.easylex.domain.use_case.notification

data class NotificationUseCases(
    val scheduleNotifications: ScheduleNotifications,
    val cancelNotifications: CancelNotifications,
    val getNotificationsTime: GetNotificationsTime,
    val isNotificationsEnabled: IsNotificationsEnabled
)