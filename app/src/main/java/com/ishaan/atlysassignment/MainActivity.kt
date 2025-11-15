package com.ishaan.atlysassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ishaan.atlysassignment.navigation.AppNavHost
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

    }
}