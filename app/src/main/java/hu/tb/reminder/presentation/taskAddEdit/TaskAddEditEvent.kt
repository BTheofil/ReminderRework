package hu.tb.reminder.presentation.taskAddEdit

import hu.tb.reminder.presentation.taskAddEdit.components.TaskNotifyRepeatTime
import java.time.LocalDate
import java.time.LocalTime

sealed class TaskAddEditEvent{
    data class OnTitleChange(val title: String): TaskAddEditEvent()
    data class OnDescriptionChange(val description: String): TaskAddEditEvent()
    object OnSaveTodoClick: TaskAddEditEvent()
    data class OnExpirationDateChange(val expirationDate: LocalDate): TaskAddEditEvent()
    data class OnExpirationTimeChange(val expirationTime: LocalTime): TaskAddEditEvent()
    data class OnRepeatTimeChange(val notifyRepeatTime: TaskNotifyRepeatTime): TaskAddEditEvent()
}
