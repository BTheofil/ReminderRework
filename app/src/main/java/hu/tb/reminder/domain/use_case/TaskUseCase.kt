package hu.tb.reminder.domain.use_case

data class TaskUseCase(
    val getTasks: GetTasks,
    val deleteTask: DeleteTask,
    val saveTask: SaveTask
)