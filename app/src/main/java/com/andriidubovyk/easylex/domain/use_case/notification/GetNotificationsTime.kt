package com.andriidubovyk.easylex.domain.use_case.notification

import com.andriidubovyk.easylex.domain.model.Time
import com.andriidubovyk.easylex.domain.repository.NotificationRepository

class GetNotificationsTime(private val repository: NotificationRepository) {

    operator fun invoke(): Time {
        return repository.getNotificationsTime()
    }
}