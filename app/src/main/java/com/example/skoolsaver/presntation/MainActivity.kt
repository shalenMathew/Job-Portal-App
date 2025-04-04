package com.example.skoolsaver.presntation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skoolsaver.presntation.add_edit_jobs.AddEditNoteScreen
import com.example.skoolsaver.presntation.home.JobScreen
import com.example.skoolsaver.presntation.theme.SkoolSaverTheme
import com.example.skoolsaver.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkoolSaverTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.JobScreen.route
                ) {

                    composable(route = Screen.JobScreen.route) {
                        JobScreen(navController)
                    }

                    composable(
                        route = Screen.AddEditJobScreen.route +
                                "?noteId={noteId}",
                        arguments = listOf(
                            navArgument(
                                name = "noteId"
                            ) {
                                type = NavType.Companion.StringType
                                defaultValue = "-1"
                            }
                        )
                    ) {
                        AddEditNoteScreen(
                            navController = navController
                        )
                    }
                }


            }
        }
    }
}