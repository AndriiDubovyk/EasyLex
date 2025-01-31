package com.andriidubovyk.easylex.domain.use_case.notification

import com.andriidubovyk.easylex.domain.repository.NotificationRepository

class IsNotificationsEnabled(private val repository: NotificationRepository) {

    operator fun invoke(): Boolean {
        return repository.isNotificationsEnabled()
    }
}