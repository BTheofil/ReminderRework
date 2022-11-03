package hu.tb.reminder.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val details: String,
    val isDone: Boolean,
    val expireDate: String,
)
