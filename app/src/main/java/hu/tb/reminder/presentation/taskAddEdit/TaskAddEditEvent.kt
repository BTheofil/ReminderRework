package hu.tb.reminder.presentation.taskAddEdit

import java.time.LocalDate

sealed class TaskAddEditEvent{
    data class OnTitleChange(val title: String): TaskAddEditEvent()
    data class OnDescriptionChange(val description: String): TaskAddEditEvent()
    object OnSaveTodoClick: TaskAddEditEvent()
    data class OnExpirationDateChange(val expirationDate: LocalDate): TaskAddEditEvent()
}
