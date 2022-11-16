package hu.tb.reminder

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import hu.tb.reminder.presentation.taskAddEdit.notify.NotificationService.Companion.TASK_WILL_EXPIRE_ID

@HiltAndroidApp
class ReminderApp: Application(){

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            TASK_WILL_EXPIRE_ID,
            "Task ready",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        channel.description = "Appear when a task near the expire date"

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}