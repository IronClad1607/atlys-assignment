package com.ishaan.atlysassignment.features.splash_screen.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ishaan.atlysassignment.R
import com.ishaan.atlysassignment.features.splash_screen.data.SplashScreenUIEvent
import com.ishaan.atlysassignment.features.splash_screen.viewmodel.SplashScreenViewModel
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme

@Composable
fun SplashScreen(
    navigateToMovieDetailScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: SplashScreenViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                SplashScreenUIEvent.Success -> {
                    navigateToMovieDetailScreen()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadApp()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_app_logo),
            contentDescription = "App logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(300.dp)
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        // Show loading indicator at the bottom
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        )
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    AtlysAssignmentTheme {
        SplashScreen(
            navigateToMovieDetailScreen = {}
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SplashScreenDarkPreview() {
    AtlysAssignmentTheme {
        SplashScreen(
            navigateToMovieDetailScreen = {}
        )
    }
}