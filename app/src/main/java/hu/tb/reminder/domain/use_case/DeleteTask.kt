package hu.tb.reminder.domain.use_case

import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.repository.TaskRepository

class DeleteTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(taskEntity: TaskEntity){
        repository.deleteTask(taskEntity)
    }
}