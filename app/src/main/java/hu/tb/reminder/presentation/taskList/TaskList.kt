package hu.tb.reminder.presentation.taskList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import hu.tb.reminder.domain.model.TaskEntity
import hu.tb.reminder.presentation.route.Routes

@Composable
fun TaskList(
    navController: NavController,
    taskListViewModel: TaskListViewModel = hiltViewModel()
){
    val items = taskListViewModel.taskList.observeAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Routes.ADD_TASK.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(
                items = items.value?: emptyList(),
                key = { task -> task.id!!}
            ){ task ->
                TaskItem(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Routes.ADD_TASK.route + "?taskId=${task.id}")
                        },
                    task = task,
                    onCheckedChange = { checked ->  taskListViewModel.changeTaskChecked(task, checked)},
                    onDeleteTask = { taskListViewModel.deleteTask(task) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TaskItem(
    modifier: Modifier = Modifier,
    task: TaskEntity,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteTask: () -> Unit
){
    Card(
        modifier = modifier
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(8.dp)
            ) {
                Text(text = task.title)
                Spacer(modifier = modifier.height(8.dp))
                Text(text = task.details)
            }
            Column(
                modifier = Modifier,
            ) {
                IconButton(
                    onClick = onDeleteTask
                ) {
                    Icon(
                        Icons.Filled.Close,
                    "Close"
                    )
                }
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = onCheckedChange
                )
            }
        }

    }
}