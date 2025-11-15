package com.ishaan.atlysassignment.navigation

/**
 * A sealed interface representing a screen route.
 * Each screen must implement this interface and define its unique route.
 */
sealed interface Routes {
    val route: String
}

data object SplashScreen : Routes {
    override val route: String
        get() = "splash_screen"
}