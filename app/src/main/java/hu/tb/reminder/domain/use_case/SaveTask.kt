package hu.tb.reminder.domain.use_case

import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.repository.TaskRepository

class SaveTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(task: TaskEntity) {
        return repository.insertTask(task)
    }
}