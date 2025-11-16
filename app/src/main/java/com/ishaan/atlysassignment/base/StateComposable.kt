// Common UI state composables used across the app to display loading, empty, and error states
package com.ishaan.atlysassignment.base

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Displays a centered circular progress indicator for loading states
@Composable
fun LoadingComposable(modifier: Modifier = Modifier) {
    // Wrapper Box to center the loading indicator on the screen
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
            // Indeterminate progress indicator to show ongoing loading
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingComposablePreview() {
    LoadingComposable()
}

// Displays a message when there's no data to show
@Composable
fun EmptyComposable(
    message: String = "No data available",
    modifier: Modifier = Modifier
) {
    // Centered container for empty state message
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
            // Message informing the user that no data is available
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview
@Composable
fun EmptyComposablePreview() {
    EmptyComposable()
}

// Displays an error message with a retry button for recovering from failures
@Composable
fun ErrorComposable(
    message: String,
    onRetryClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Container that centers the error content on the screen
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Column to display the error message and retry action vertically
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Error message styled using MaterialTheme error colors
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.error
            )
            // Retry button allowing the user to attempt the failed action again
            Button(
                onClick = {
                    // Invoke the retry callback supplied by the caller
                    onRetryClicked()
                }
            ) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview
@Composable
fun ErrorComposablePreview() {
    ErrorComposable(
        message = "Something went wrong", onRetryClicked = {}
    )
}