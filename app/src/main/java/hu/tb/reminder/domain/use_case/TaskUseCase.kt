package hu.tb.reminder.domain.use_case

import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.repository.TaskRepository
import javax.inject.Inject

class TaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {

    suspend fun getTaskById(id: Int): TaskEntity? = repository.getTaskById(id)

    fun getTaskList() = repository.getTasks()

    suspend fun saveTask(taskEntity: TaskEntity): Long = repository.insertTask(taskEntity)

    suspend fun deleteTask(taskEntity: TaskEntity) = repository.deleteTask(taskEntity)
}