package hu.tb.reminder.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.tb.reminder.data.data_source.TaskDatabase
import hu.tb.reminder.data.repository.TaskRepositoryImpl
import hu.tb.reminder.domain.repository.TaskRepository
import hu.tb.reminder.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(app: Application): TaskDatabase {
        return Room.databaseBuilder(
            app,
            TaskDatabase::class.java,
            TaskDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(db: TaskDatabase): TaskRepository {
        return TaskRepositoryImpl(db.taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCases(repository: TaskRepository): TaskUseCase {
        return TaskUseCase(
            getTasks = GetTasks(repository),
            deleteTask = DeleteTask(repository),
            saveTask = SaveTask(repository),
            getTask = GetTask(repository),
        )
    }
}