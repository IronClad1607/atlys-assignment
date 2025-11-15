package com.ishaan.atlysassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ishaan.atlysassignment.features.movie_list.ui.MovieListScreen
import com.ishaan.atlysassignment.features.splash_screen.ui.SplashScreen
import com.ishaan.atlysassignment.navigation.AppNavHost
import com.ishaan.atlysassignment.navigation.MovieListScreen
import com.ishaan.atlysassignment.navigation.SplashScreen
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AtlysAssignmentTheme {
                val navHostController = rememberNavController()
                App(
                    navController = navHostController,
                    modifier = Modifier.safeDrawingPadding()
                )
            }
        }
    }
}

@Composable
fun App(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    AppNavHost(
        navHostController = navController,
        modifier = modifier
    ) {
        composable(
            route = SplashScreen.route
        ) {
            SplashScreen(
                navigateToMovieDetailScreen = {
                    navController.navigate(MovieListScreen.route) {
                        popUpTo(SplashScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = MovieListScreen.route
        ) {
            MovieListScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}