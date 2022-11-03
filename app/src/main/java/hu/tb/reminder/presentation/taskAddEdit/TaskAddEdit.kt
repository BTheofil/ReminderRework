package hu.tb.reminder.presentation.taskAddEdit

import hu.tb.reminder.presentation.taskAddEdit.components.DatePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate

@Composable
fun TaskAddEditScreen(
    viewModel: TaskAddEditViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Add new task")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, "Back")
                    }
                },


                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(TaskAddEditEvent.OnSaveTodoClick)
                },
            ) {
                Icon(Icons.Rounded.Save, contentDescription = "Save")
            }
        },
        content = { innerPadding ->
            LaunchedEffect(key1 = true) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is TaskAddEditState.ShowSnackBar -> {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = "Title can not be empty"
                            )
                        }
                        is TaskAddEditState.SaveNote -> {
                            navController.navigateUp()
                        }
                    }
                }
            }
            TaskAddEditForm(
                modifier = Modifier.padding(innerPadding),
                titleText = viewModel.title,
                descriptionText = viewModel.description,
                expirationDateText = viewModel.expirationDate.toString(),
                expirationDate = {
                    viewModel.onEvent(TaskAddEditEvent.OnExpirationDateChange(it))
                },
                onTitleValueChange = { viewModel.onEvent(TaskAddEditEvent.OnTitleChange(it)) },
                onDescriptionValueChange = {
                    viewModel.onEvent(
                        TaskAddEditEvent.OnDescriptionChange(
                            it
                        )
                    )
                },
            )
        }
    )
}

@Composable
private fun TaskAddEditForm(
    modifier: Modifier,
    titleText: String,
    descriptionText: String,
    expirationDateText: String,
    expirationDate: (date: LocalDate) -> Unit,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
) {
    var isTitleFocused by remember { mutableStateOf(true) }
    var isDescriptionFocused by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        val dialogState = rememberMaterialDialogState()
        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            datepicker(
                onDateChange = expirationDate
            )
        }

        DatePicker(
            selectedDate = expirationDateText,
            onClick = {
                dialogState.show()
            }
        )

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
                    isTitleFocused = when {
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
                        if (!isTitleFocused && titleText.isEmpty()) {
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
                .onFocusChanged { focusState ->
                    isDescriptionFocused = when {
                        focusState.isFocused -> {
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
                .padding(
                    start = 24.dp,
                    top = 8.dp
                ),
            value = descriptionText,
            onValueChange = onDescriptionValueChange,
            decorationBox = { innerTextField ->
                Row(modifier = Modifier) {
                    if (!isDescriptionFocused && descriptionText.isEmpty()) {
                        Text("Description your task...")
                    }
                    innerTextField()
                }
            }
        )
    }
}