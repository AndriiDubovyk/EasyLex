package com.andriidubovyk.easylex.domain.use_case.notification

import com.andriidubovyk.easylex.domain.repository.NotificationRepository

class CancelNotifications(
    private val repository: NotificationRepository
) {

    operator fun invoke() {
        repository.cancelNotification()
    }
}