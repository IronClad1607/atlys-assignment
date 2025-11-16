package com.ishaan.atlysassignment.features.movie_list.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ishaan.atlysassignment.features.movie_list.viewmodel.MovieListViewModel
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme

private const val PAGINATION_THRESHOLD = 6

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<MovieListViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyGridState()

    LaunchedEffect(Unit) {
        viewModel.getTrendingMovies()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Trending Movies"
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            state = listState,
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                uiState.movies
            ) { movie ->
                MovieItem(
                    posterPath = movie.posterPath,
                    title = movie.title,
                    onClick = {

                    }
                )
            }

            if (uiState.isLoading) {
                item(
                    span = {
                        GridItemSpan(2)
                    }
                ) {
                    LoadingIndicator()
                }
            }
        }

        val shouldLoadMore by remember {
            derivedStateOf {
                val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                    ?: return@derivedStateOf false // List is empty

                val totalItems = listState.layoutInfo.totalItemsCount

                // Check if the last visible item's index is at or past the threshold
                lastVisibleItem.index >= totalItems - 1 - PAGINATION_THRESHOLD
            }
        }

        LaunchedEffect(shouldLoadMore, uiState.isLoading) {
            if (shouldLoadMore && !uiState.isLoading && !uiState.allMoviesLoaded) {
                viewModel.getTrendingMovies()
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun MovieListScreenPreview() {
    AtlysAssignmentTheme {
        MovieListScreen()
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MovieListScreenDarkPreview() {
    AtlysAssignmentTheme {
        MovieListScreen()
    }
}