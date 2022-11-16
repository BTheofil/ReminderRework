package hu.tb.reminder.presentation.taskList

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.use_case.TaskUseCase
import hu.tb.reminder.presentation.taskAddEdit.notify.AlarmReceiver
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase,
    private val application: Application
): ViewModel() {

    val taskList: LiveData<List<TaskEntity>> = taskUseCase.getTaskList()

    fun changeTaskChecked(item: TaskEntity, checked: Boolean) {
        viewModelScope.launch {
            taskUseCase.saveTask(TaskEntity(
                id = item.id,
                title = item.title,
                details = item.details,
                isDone = checked,
                expireDate = item.expireDate,
                expireTime = item.expireTime
            ))
        }
    }

    fun deleteTask(taskEntity: TaskEntity){
        viewModelScope.launch {
            taskUseCase.deleteTask(taskEntity)
            val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(application, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                application.applicationContext,
                taskEntity.id!!,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
            alarmManager.cancel(pendingIntent)
        }
    }
}