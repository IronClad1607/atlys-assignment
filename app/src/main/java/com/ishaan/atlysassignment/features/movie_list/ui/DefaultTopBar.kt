// Default top app bar used on the Movie List screen, showing the title and a search action icon.
package com.ishaan.atlysassignment.features.movie_list.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Opting into experimental Material 3 APIs to use TopAppBar components.
@OptIn(ExperimentalMaterial3Api::class)
// Reusable top bar composable containing the Trending Movies title and search action button.
@Composable
fun DefaultTopBar(
    // Callback triggered when the search icon is clicked.
    onSearchIconClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Main TopAppBar providing title, colors, and action icons.
    TopAppBar(
        title = {
            // Title displayed in the app bar.
            Text(
                text = "Trending Movies"
            )
        },
        // Define background and content colors for the top app bar.
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        actions = {
            // Search icon button placed on the right side of the top bar.
            IconButton(
                // Trigger search callback when the search icon is pressed.
                onClick = {
                    onSearchIconClicked()
                }
            ) {
                // Search icon graphic shown in the top bar.
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Movies",
                )
            }
        },
        // Apply full width and add subtle shadow for elevation.
        modifier = modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp)
    )
}

// Preview for testing the DefaultTopBar layout in Android Studio.
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DefaultTopBarPreview() {
    DefaultTopBar(onSearchIconClicked = {})
}