package hu.tb.reminder.presentation.addTask

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import hu.tb.reminder.domain.model.TaskEntity

@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel = hiltViewModel(),
    navController: NavHostController
) {
    var titleText by remember { mutableStateOf("") }
    var descriptionText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Add new task")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "")
                    }
                },


                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveTask(
                        TaskEntity(
                            title = titleText,
                            details = descriptionText,
                            isDone = false
                        )
                    )
                },
            ) {
                Icon(Icons.Rounded.Save, contentDescription = "Save")
            }
        }
    ) {
        AddTaskForm(
            titleText = titleText,
            descriptionText = descriptionText,
            onTitleValueChange = { titleText = it },
            onDescriptionValueChange = { descriptionText = it },
        )
    }
}

@Composable
private fun AddTaskForm(
    titleText: String,
    descriptionText: String,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
) {
    var isFocused by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = ""
            )
            BasicTextField(
                modifier = Modifier.onFocusChanged { focusState ->
                    isFocused = when {
                        focusState.isFocused -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                },
                value = titleText,
                onValueChange = onTitleValueChange,
                singleLine = true,
                textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                decorationBox = { innerTextField ->
                    Row(modifier = Modifier) {
                        if (!isFocused && titleText.isEmpty()) {
                            Text("Title your task...")
                        }
                        innerTextField()
                    }
                }
            )
        }
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
        BasicTextField(
            modifier = Modifier
                .padding(start = 24.dp)
                .background(Color.Red),
            value = descriptionText,
            onValueChange = onDescriptionValueChange,
        )
    }
}