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
import androidx.navigation.toRoute
import com.ishaan.atlysassignment.features.movie_detail.data.MovieDetailsArgs
import com.ishaan.atlysassignment.features.movie_detail.data.MovieDetailsArgsNavType
import com.ishaan.atlysassignment.features.movie_detail.ui.MovieDetailScreen
import com.ishaan.atlysassignment.features.movie_list.ui.MovieListScreen
import com.ishaan.atlysassignment.features.splash_screen.ui.SplashScreen
import com.ishaan.atlysassignment.navigation.AppNavHost
import com.ishaan.atlysassignment.navigation.MovieDetailScreenRoute
import com.ishaan.atlysassignment.navigation.MovieListScreenRoute
import com.ishaan.atlysassignment.navigation.SplashScreenRoute
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

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
        composable<SplashScreenRoute> {
            SplashScreen(
                navigateToMovieDetailScreen = {
                    navController.navigate(MovieListScreenRoute) {
                        popUpTo(SplashScreenRoute) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<MovieListScreenRoute> {
            MovieListScreen(
                navigateToMovieDetail = {
                    navController.navigate(MovieDetailScreenRoute(it))
                }
            )
        }

        composable<MovieDetailScreenRoute>(
            typeMap = mapOf(
                typeOf<MovieDetailsArgs>() to MovieDetailsArgsNavType()
            )
        ) { backStackEntry ->
            val route = backStackEntry.toRoute<MovieDetailScreenRoute>()
            val args = route.movieDetailsArgs
            MovieDetailScreen(
                args = args,
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}