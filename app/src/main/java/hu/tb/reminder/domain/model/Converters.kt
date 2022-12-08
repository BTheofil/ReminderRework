package hu.tb.reminder.domain.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter

@ProvidedTypeConverter
class Converters {

    @TypeConverter
    fun toTaskNotifyRepeatTime(value: String) = enumValueOf<TaskNotifyRepeatTime>(value)

    @TypeConverter
    fun fromTaskNotifyRepeatTime(value: TaskNotifyRepeatTime) = value.name
}