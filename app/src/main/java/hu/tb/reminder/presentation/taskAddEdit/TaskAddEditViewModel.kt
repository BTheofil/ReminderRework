package hu.tb.reminder.presentation.taskAddEdit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.use_case.TaskUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import hu.tb.reminder.presentation.taskAddEdit.TaskAddEditEvent.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@HiltViewModel
class TaskAddEditViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    var task by mutableStateOf<TaskEntity?>(null)
        private set

    private val _eventFlow = MutableSharedFlow<TaskAddEditState>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        savedStateHandle.get<String>("taskId")?.let { taskId ->
            viewModelScope.launch {
                taskUseCase.getTask(taskId.toInt())?.let { taskEntity ->
                    task = taskEntity
                    title = taskEntity.title
                    description = taskEntity.details
                }
            }
        }
    }

    fun onEvent(event: TaskAddEditEvent){
        when(event){
            is OnTitleChange -> {
                title = event.title
            }

            is OnDescriptionChange -> {
                description = event.description
            }

            is OnSaveTodoClick -> {
                viewModelScope.launch {
                    if(title.isNotEmpty() ){
                        taskUseCase.saveTask(
                            TaskEntity(
                                id = task?.id,
                                title = title,
                                details = description,
                                isDone = false
                            )
                        )
                        _eventFlow.emit(TaskAddEditState.SaveNote)
                    } else {
                        _eventFlow.emit(TaskAddEditState.ShowSnackBar)
                    }

                }
            }
        }
    }
}