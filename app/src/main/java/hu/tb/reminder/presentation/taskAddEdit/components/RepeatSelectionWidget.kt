package hu.tb.reminder.presentation.taskAddEdit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun RepeatSelectionWidget(
    modifier: Modifier,
    taskNotifyRepeatTime: (TaskNotifyRepeatTime) -> Unit,
) {
    var dailyBtn by rememberSaveable {
        mutableStateOf(true)
    }

    var weeklyBtn by rememberSaveable {
        mutableStateOf(true)
    }

    var monthlyBtn by rememberSaveable {
        mutableStateOf(true)
    }

    Column(
        modifier
    ) {
        Text(text = "Select repeat period:")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                enabled = dailyBtn,
                onClick = {
                    taskNotifyRepeatTime(TaskNotifyRepeatTime.DAILY)
                    dailyBtn = false
                    weeklyBtn = true
                    monthlyBtn = true
                }
            ) {
                Text(text = "Daily")
            }
            Button(
                enabled = weeklyBtn,
                onClick = {
                    taskNotifyRepeatTime(TaskNotifyRepeatTime.WEEKLY)
                    dailyBtn = true
                    weeklyBtn = false
                    monthlyBtn = true
                }) {
                Text(text = "Weekly")
            }
            Button(
                enabled = monthlyBtn,
                onClick = {
                    taskNotifyRepeatTime(TaskNotifyRepeatTime.MONTHLY)
                    dailyBtn = true
                    weeklyBtn = true
                    monthlyBtn = false
                }) {
                Text(text = "Monthly")
            }
        }
    }
}