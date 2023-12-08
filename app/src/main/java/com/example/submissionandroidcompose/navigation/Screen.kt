package com.example.submissionandroidcompose.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object About : Screen("about")
    object Detail : Screen("home/{username}") {
        fun createDetailRoute(username: String) = "home/$username"
    }
}