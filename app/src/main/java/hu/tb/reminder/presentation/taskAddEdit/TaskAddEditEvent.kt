package hu.tb.reminder.presentation.taskAddEdit

sealed class TaskAddEditEvent{
    data class OnTitleChange(val title: String): TaskAddEditEvent()
    data class OnDescriptionChange(val description: String): TaskAddEditEvent()
    object OnSaveTodoClick: TaskAddEditEvent()
}
