package hu.tb.reminder.data.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.tb.reminder.domain.model.TaskEntity

@Dao
interface TaskDao {

    @Query("SELECT * FROM TaskEntity")
    fun getTasks(): LiveData<List<TaskEntity>>

    @Query("SELECT * FROM TaskEntity WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(taskEntity: TaskEntity): Long

    @Delete
    suspend fun deleteNote(taskEntity: TaskEntity)
}