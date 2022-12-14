package hu.tb.reminder.presentation.taskAddEdit

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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import hu.tb.reminder.presentation.taskAddEdit.components.OpenDialogPicker
import hu.tb.reminder.presentation.taskAddEdit.components.RepeatSelectionWidget
import hu.tb.reminder.domain.model.TaskNotifyRepeatTime
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import java.time.LocalTime

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
                expirationTimeText = viewModel.expirationTime.toString(),
                expirationTime = {
                    viewModel.onEvent(TaskAddEditEvent.OnExpirationTimeChange(it))
                },
                onTitleValueChange = { viewModel.onEvent(TaskAddEditEvent.OnTitleChange(it)) },
                onDescriptionValueChange = {
                    viewModel.onEvent(
                        TaskAddEditEvent.OnDescriptionChange(
                            it
                        )
                    )
                },
                onRepeatPeriodValueChange = {
                    viewModel.onEvent(TaskAddEditEvent.OnRepeatTimeChange(it))
                }
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
    expirationTimeText: String,
    expirationDate: (LocalDate) -> Unit,
    expirationTime: (LocalTime) -> Unit,
    onTitleValueChange: (String) -> Unit,
    onDescriptionValueChange: (String) -> Unit,
    onRepeatPeriodValueChange: (TaskNotifyRepeatTime) -> Unit
) {
    val constraints = ConstraintSet {
        val card = createRefFor("card")
        val dateTimePickers = createRefFor("pickers")
        val repeatButtons = createRefFor("repeatBtn")

        constrain(card){
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(dateTimePickers){
            top.linkTo(card.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(repeatButtons){
            top.linkTo(dateTimePickers.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
    
    var isTitleFocused by remember { mutableStateOf(true) }
    var isDescriptionFocused by remember { mutableStateOf(true) }

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

    val dialogTimeState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogTimeState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        timepicker(
            onTimeChange = expirationTime
        )
    }

    ConstraintLayout(constraints, Modifier.fillMaxSize()) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .border(BorderStroke(1.dp, Color.LightGray), shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .layoutId("card"),
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

        Row(Modifier
            .layoutId("pickers")
        ) {
            OpenDialogPicker(
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                selectedDate = expirationDateText,
                onClick = {
                    dialogState.show()
                }
            )

            OpenDialogPicker(
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                selectedDate = expirationTimeText,
                onClick = {
                    dialogTimeState.show()
                }
            )
        }

        //todo do something
        RepeatSelectionWidget(
            modifier = Modifier.layoutId("repeatBtn"),
            taskNotifyRepeatTime = onRepeatPeriodValueChange
        )
    }

}

