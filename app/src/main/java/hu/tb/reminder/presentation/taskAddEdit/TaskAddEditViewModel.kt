package hu.tb.reminder.presentation.taskAddEdit

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.use_case.TaskUseCase
import hu.tb.reminder.presentation.taskAddEdit.TaskAddEditEvent.*
import hu.tb.reminder.presentation.taskAddEdit.components.TaskNotifyRepeatTime
import hu.tb.reminder.presentation.taskAddEdit.notify.AlarmReceiver
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TaskAddEditViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase,
    savedStateHandle: SavedStateHandle,
    private val application: Application,
): ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private var task by mutableStateOf<TaskEntity?>(null)

    var expirationDate by mutableStateOf<LocalDate?>(null)
        private set

    var expirationTime by mutableStateOf<LocalTime?>(null)
        private set

    private var repeatTime by mutableStateOf<TaskNotifyRepeatTime?>(null)

    private val _eventFlow = MutableSharedFlow<TaskAddEditState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("taskId")?.let { taskId ->
            viewModelScope.launch {
                taskUseCase.getTaskById(taskId.toInt())?.let { taskEntity ->
                    task = taskEntity
                    title = taskEntity.title
                    description = taskEntity.details
                    expirationDate = LocalDate.parse(taskEntity.expireDate, DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH))
                    expirationTime = LocalTime.parse(taskEntity.expireTime, DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.ENGLISH))
                }
            }
        }
    }

    fun onEvent(event: TaskAddEditEvent){
        when(event){
            is OnTitleChange -> {
                title = event.title
            }

            is OnDescriptionChange -> {
                description = event.description
            }

            is OnSaveTodoClick -> {
                viewModelScope.launch {
                    if(title.isNotEmpty() ){

                        val task = TaskEntity(
                            id = task?.id,
                            title = title,
                            details = description,
                            isDone = false,
                            expireDate = expirationDate.toString(),
                            expireTime = expirationTime.toString()
                        )

                        val id = taskUseCase.saveTask(task)
                        if(expirationDate != null && expirationTime != null){
                            setAlarm(taskUseCase.getTaskById(id.toInt())!!)
                        }
                        _eventFlow.emit(TaskAddEditState.SaveNote)
                    } else {
                        _eventFlow.emit(TaskAddEditState.ShowSnackBar)
                    }

                }
            }

            is OnExpirationDateChange -> {
                expirationDate = event.expirationDate
            }
            is OnExpirationTimeChange -> {
                expirationTime = event.expirationTime
            }

            is OnRepeatTimeChange -> {
                repeatTime = event.notifyRepeatTime
            }
        }
    }

    private fun setAlarm(task: TaskEntity) {
        val calendarDate: Calendar = Calendar.getInstance()
        calendarDate.set(
            expirationDate!!.year,
            expirationDate!!.month.value - 1, //always add +1 month
            expirationDate!!.dayOfMonth,
            expirationTime!!.hour,
            expirationTime!!.minute,
            0
        )

        val alarmManager = application.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(application, AlarmReceiver::class.java)
        intent.putExtra("key", task)
        val pendingIntent = PendingIntent.getBroadcast(
            application,
            task.id!!,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        /**
         * https://developer.android.com/training/scheduling/alarms#inexact-during-time-window
         * ELAPSED_REALTIME — Fires the pending intent based on the amount of time since the device was booted, but doesn't wake up the device.
         * The elapsed time includes any time during which the device was asleep.
         * ELAPSED_REALTIME_WAKEUP — Wakes up the device and fires the pending intent after the specified length of time has elapsed since device boot.
         * RTC — Fires the pending intent at the specified time but does not wake up the device.
         * RTC_WAKEUP — Wakes up the device to fire the pending intent at the specified time.
         */
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            calendarDate.timeInMillis,
            pendingIntent
        )
    }
}