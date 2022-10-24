package hu.tb.reminder.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import hu.tb.reminder.domain.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase: RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        const val DATABASE_NAME = "tasks_db"
    }
}