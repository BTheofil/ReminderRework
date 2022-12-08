package hu.tb.reminder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
@TypeConverters(Converters::class)
data class TaskEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val details: String,
    val isDone: Boolean,
    val expireDate: String,
    val expireTime: String,
    val repeatTime: TaskNotifyRepeatTime
): java.io.Serializable
