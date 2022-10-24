package hu.tb.reminder.presentation.taskList

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.livedata.observeAsState
import hu.tb.reminder.domain.model.TaskEntity

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
                    navController.navigate("addTask")
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
                    task = task,
                    onCheckedChange = { checked ->  taskListViewModel.changeTaskChecked(task, checked)}
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
    onCheckedChange: (Boolean) -> Unit
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
            Checkbox(
                checked = task.isDone,
                onCheckedChange = onCheckedChange
            )
        }

    }
}