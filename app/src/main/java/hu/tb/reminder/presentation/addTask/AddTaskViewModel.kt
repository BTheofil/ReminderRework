package hu.tb.reminder.presentation.addTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.domain.use_case.TaskUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val taskUseCase: TaskUseCase
): ViewModel() {

    fun saveTask(task: TaskEntity){
        viewModelScope.launch {
            taskUseCase.saveTask(task)
        }
    }
}