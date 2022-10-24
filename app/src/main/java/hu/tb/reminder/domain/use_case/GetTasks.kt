package hu.tb.reminder.domain.use_case

import androidx.lifecycle.LiveData
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.repository.TaskRepository

class GetTasks(
    private val repository: TaskRepository
) {

    operator fun invoke(): LiveData<List<TaskEntity>> {
        return repository.getTasks()
    }
}