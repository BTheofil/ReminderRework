package hu.tb.reminder.data.repository

import androidx.lifecycle.LiveData
import hu.tb.reminder.data.data_source.TaskDao
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val dao: TaskDao
): TaskRepository {

    override fun getTasks(): LiveData<List<TaskEntity>> {
        return dao.getTasks()
    }

    override suspend fun getTaskById(id: Int): TaskEntity? {
        return dao.getTaskById(id)
    }

    override suspend fun insertTask(taskEntity: TaskEntity): Long {
        return dao.insertNote(taskEntity)
    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        return dao.deleteNote(taskEntity)
    }
}