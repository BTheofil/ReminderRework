package hu.tb.reminder.presentation.taskAddEdit.notify

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import hu.tb.reminder.domain.model.TaskEntity

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent != null) {
            if (Build.VERSION.SDK_INT >= 33) {
                val task = intent.getSerializableExtra("key", TaskEntity::class.java)
                if (context != null) {
                    NotificationService(context).showNotification(task!!)
                }
            } else {
                val task = intent.extras?.getSerializable("key") as TaskEntity
                if (context != null) {
                    NotificationService(context).showNotification(task)
                }
            }
        }
    }

}