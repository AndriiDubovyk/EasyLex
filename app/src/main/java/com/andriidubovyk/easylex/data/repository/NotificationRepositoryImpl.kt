package com.andriidubovyk.easylex.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.andriidubovyk.easylex.R
import com.andriidubovyk.easylex.domain.model.Time
import com.andriidubovyk.easylex.domain.repository.NotificationRepository
import com.andriidubovyk.easylex.presentation.screen.settings.utils.NotificationReceiver
import java.util.Calendar
import androidx.core.content.edit

class NotificationRepositoryImpl(private val context: Context) : NotificationRepository {
    private val sharedPref = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val alarmPendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        Intent(context, NotificationReceiver::class.java),
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    override fun scheduleNotifications(time: Time) {
        sharedPref.edit {
            putBoolean(context.getString(R.string.saved_notification_enabled), true)
            putInt(context.getString(R.string.saved_notification_hour), time.hour)
            putInt(context.getString(R.string.saved_notification_minute), time.minute)
        }
        alarmManager.cancel(alarmPendingIntent)
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
        }
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            alarmPendingIntent
        )
    }

    override fun cancelNotification() {
        sharedPref.edit {
            putBoolean(context.getString(R.string.saved_notification_enabled), false)
        }
        alarmManager.cancel(alarmPendingIntent)
    }

    override fun isNotificationsEnabled(): Boolean {
        return sharedPref.getBoolean(context.getString(R.string.saved_notification_enabled), false)
    }

    override fun getNotificationsTime(): Time {
        return Time(
            hour = sharedPref.getInt(context.getString(R.string.saved_notification_hour), 21),
            minute = sharedPref.getInt(context.getString(R.string.saved_notification_minute), 0),
        )
    }
}