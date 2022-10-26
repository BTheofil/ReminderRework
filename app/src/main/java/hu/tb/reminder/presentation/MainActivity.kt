package hu.tb.reminder.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.tb.reminder.presentation.taskAddEdit.TaskAddEditScreen
import hu.tb.reminder.presentation.route.Routes
import hu.tb.reminder.presentation.taskList.TaskList
import hu.tb.reminder.ui.theme.ReminderTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReminderTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.TASK_LIST.route) {
                    composable(route = Routes.TASK_LIST.route) {
                        TaskList(
                            navController = navController,
                        )
                    }
                    composable(route = Routes.ADD_TASK.route + "?taskId={taskId}",
                        arguments = listOf(
                            navArgument(
                                name = "taskId"
                            ) {
                                type = NavType.StringType
                                nullable = true
                                defaultValue = null
                            },
                        )
                    ) {
                        TaskAddEditScreen(
                            navController = navController,
                        )
                    }
                }

            }
        }
    }
}