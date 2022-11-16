package hu.tb.reminder.domain.repository

import androidx.lifecycle.LiveData
import hu.tb.reminder.domain.model.TaskEntity

interface TaskRepository {

    fun getTasks(): LiveData<List<TaskEntity>>

    suspend fun getTaskById(id: Int): TaskEntity?

    suspend fun insertTask(taskEntity: TaskEntity): Long

    suspend fun deleteTask(taskEntity: TaskEntity)
}