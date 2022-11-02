package hu.tb.reminder.presentation.taskAddEdit

sealed class TaskAddEditState {
    object ShowSnackBar: TaskAddEditState()
    object SaveNote: TaskAddEditState()
}