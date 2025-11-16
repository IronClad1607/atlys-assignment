// Screen responsible for displaying full details of a selected movie, including poster, title, and overview
package com.ishaan.atlysassignment.features.movie_detail.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ishaan.atlysassignment.R
import com.ishaan.atlysassignment.data.network.BaseURL
import com.ishaan.atlysassignment.features.movie_detail.data.MovieDetailsArgs

// Opting into experimental Material 3 APIs to use TopAppBar components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    args: MovieDetailsArgs,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Scaffold provides the top-level layout structure with a TopAppBar and content area
    Scaffold(
        topBar = {
            // Top bar with back navigation icon
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        // Trigger navigation back to previous screen
                        navigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate Back"
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        // Main vertical scrollable content container for movie details
        Column(
            // Using Column to stack poster, title, and overview vertically
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card displaying the movie poster image in a square aspect ratio
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                // Load movie poster asynchronously using Coil with placeholder and error fallback
                AsyncImage(
                    model = "${BaseURL.IMAGE_BASE_URL}${args.backdropPath}",
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = painterResource(com.ishaan.atlysassignment.R.drawable.ic_placeholder_image),
                    error = painterResource(R.drawable.ic_error_image)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Display movie title with larger font
            Text(
                text = args.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 24.sp
                ),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Display movie description / overview text
            Text(
                text = args.overview,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                ),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

// Preview of MovieDetailScreen using sample data
@Preview
@Composable
fun MovieDetailScreenPreview() {
    val args = MovieDetailsArgs(
        title = "Sample Movie Title",
        overview = "This is a sample overview of the movie.",
        backdropPath = "/hZkgoQYus5OM1kZvMzAOMeyBofI.jpg"
    )
    MovieDetailScreen(
        args = args,
        navigateBack = {}
    )
}
