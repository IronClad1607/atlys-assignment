// Main screen displaying trending movies in a grid layout with integrated search, loading, empty, and error states.
package com.ishaan.atlysassignment.features.movie_list.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ishaan.atlysassignment.base.EmptyComposable
import com.ishaan.atlysassignment.base.ErrorComposable
import com.ishaan.atlysassignment.base.LoadingComposable
import com.ishaan.atlysassignment.features.movie_detail.data.MovieDetailsArgs
import com.ishaan.atlysassignment.features.movie_list.viewmodel.MovieListViewModel
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme

// Opting into experimental Material 3 APIs for using components like AnimatedContent and TopAppBar.
@OptIn(ExperimentalMaterial3Api::class)
// Primary composable for the Movie List screen, handling UI state, search bar, and movie grid rendering.
@Composable
fun MovieListScreen(
    navigateToMovieDetail: (movie: MovieDetailsArgs) -> Unit,
    modifier: Modifier = Modifier
) {
    // Inject MovieListViewModel using Hilt for state and business logic.
    val viewModel = hiltViewModel<MovieListViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyGridState()

    // Trigger initial movie fetch when the screen is first composed.
    LaunchedEffect(Unit) {
        viewModel.getTrendingMovies()
    }

    Scaffold(
        topBar = {
            // Animated transition between default top bar and search bar based on search state.
            AnimatedContent(
                targetState = uiState.isSearchOpen,
                label = "Search Bar Animation"
            ) { isSearchActive ->
                if (isSearchActive) {
                    // Display active SearchBar when the search state is open.
                    SearchBar(
                        searchText = uiState.searchText,
                        onSearchTextChange = {
                            viewModel.onSearchUpdate(it)
                        },
                        onCloseClick = {
                            viewModel.onSearchIconClicked(false)
                        }
                    )
                } else {
                    // Display the default top bar when search is closed.
                    DefaultTopBar(
                        onSearchIconClicked = {
                            viewModel.onSearchIconClicked(true)
                        }
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        // Show loading indicator while data is being fetched.
        if (uiState.isLoading) {
            LoadingComposable(
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            // If no error, continue rendering movie list or empty state.
            if (uiState.errorMessage.isNullOrEmpty()) {
                // Show empty state message when search yields no results.
                if (uiState.filteredMovies.isEmpty()) {
                    EmptyComposable(
                        message = "No movies found.",
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    // Display the list of movies in a vertical scrolling 2-column grid layout.
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = listState,
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(horizontal = 16.dp)
                            .padding(top = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            uiState.filteredMovies
                        ) { movie ->
                            // Individual movie item showing poster and title; navigates to detail screen on click.
                            MovieItem(
                                posterPath = movie.posterPath,
                                title = movie.title,
                                onClick = {
                                    navigateToMovieDetail(
                                        MovieDetailsArgs(
                                            title = movie.title,
                                            overview = movie.overview,
                                            backdropPath = movie.posterPath
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
            } else {
                // Display error message with a retry option when API call fails.
                ErrorComposable(
                    message = uiState.errorMessage
                        ?: "Something went wrong, Try again after sometime.",
                    onRetryClicked = {
                        viewModel.getTrendingMovies()
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

// Preview of the MovieListScreen for design and layout testing.
@Preview
@Composable
private fun MovieListScreenPreview() {
    AtlysAssignmentTheme {
        MovieListScreen(
            navigateToMovieDetail = {}
        )
    }
}

// Preview of the MovieListScreen for design and layout testing.
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MovieListScreenDarkPreview() {
    AtlysAssignmentTheme {
        MovieListScreen(
            navigateToMovieDetail = {}
        )
    }
}