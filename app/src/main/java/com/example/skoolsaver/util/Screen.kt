package com.example.skoolsaver.util

sealed class Screen(val route: String) {
    object JobScreen: Screen("jobs_screen")
    object AddEditJobScreen: Screen("add_edit_job_screen")
}
