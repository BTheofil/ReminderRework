package hu.tb.reminder.presentation.taskList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.use_case.TaskUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
): ViewModel() {

    val taskList: LiveData<List<TaskEntity>> = taskUseCase.getTasks()

    fun changeTaskChecked(item: TaskEntity, checked: Boolean) {
        viewModelScope.launch {
            taskUseCase.saveTask(TaskEntity(
                id = item.id,
                title = item.title,
                details = item.details,
                isDone = checked
            ))
        }
    }
}