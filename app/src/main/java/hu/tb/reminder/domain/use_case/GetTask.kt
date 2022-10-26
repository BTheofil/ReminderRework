package hu.tb.reminder.domain.use_case

import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.repository.TaskRepository

class GetTask(
    private val repository: TaskRepository
) {

    suspend operator fun invoke(id: Int) : TaskEntity? = repository.getTaskById(id)

}