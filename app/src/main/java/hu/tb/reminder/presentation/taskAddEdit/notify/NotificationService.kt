package hu.tb.reminder.presentation.taskAddEdit.notify

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import hu.tb.reminder.R
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.presentation.MainActivity

class NotificationService(
    private val context: Context
){
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(task: TaskEntity) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            OPEN_APP_CODE,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, TASK_WILL_EXPIRE_ID)
            .setSmallIcon(R.drawable.ic_outline_calendar_month_24)
            .setContentTitle("Task will be expire")
            .setContentText(task.title)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(task.id!!, notification)
    }

    companion object {
        const val TASK_WILL_EXPIRE_ID = "task-expire-id"
        const val OPEN_APP_CODE = 1
    }

}