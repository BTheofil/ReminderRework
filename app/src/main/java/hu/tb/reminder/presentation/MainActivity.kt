package hu.tb.reminder.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.tb.reminder.presentation.addTask.AddTaskScreen
import hu.tb.reminder.presentation.taskList.TaskList
import hu.tb.reminder.ui.theme.ReminderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReminderTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "taskList") {
                    composable("taskList") {
                        TaskList(
                            navController = navController,
                        )
                    }
                    composable("addTask") {
                        AddTaskScreen(
                            navController = navController,
                        )
                    }
                }

            }
        }
    }
}