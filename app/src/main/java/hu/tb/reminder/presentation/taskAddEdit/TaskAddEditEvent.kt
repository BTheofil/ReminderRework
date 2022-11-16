package hu.tb.reminder.presentation.taskAddEdit

import java.time.LocalDate
import java.time.LocalTime

sealed class TaskAddEditEvent{
    data class OnTitleChange(val title: String): TaskAddEditEvent()
    data class OnDescriptionChange(val description: String): TaskAddEditEvent()
    object OnSaveTodoClick: TaskAddEditEvent()
    data class OnExpirationDateChange(val expirationDate: LocalDate): TaskAddEditEvent()
    data class OnExpirationTimeChange(val expirationTime: LocalTime): TaskAddEditEvent()
}
