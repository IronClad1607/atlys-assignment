package com.ishaan.atlysassignment.features.movie_list.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ishaan.atlysassignment.features.movie_detail.data.MovieDetailsArgs
import com.ishaan.atlysassignment.features.movie_list.viewmodel.MovieListViewModel
import com.ishaan.atlysassignment.ui.theme.AtlysAssignmentTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navigateToMovieDetail: (movie: MovieDetailsArgs) -> Unit,
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
            AnimatedContent(
                targetState = uiState.isSearchOpen,
                label = "Search Bar Animation"
            ) { isSearchActive ->
                if (isSearchActive) {
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
                    TopAppBar(
                        title = {
                            Text(
                                text = "Trending Movies"
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            titleContentColor = MaterialTheme.colorScheme.onSecondary,
                            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        actions = {
                            IconButton(
                                onClick = {
                                    viewModel.onSearchIconClicked(true)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Movies",
                                )
                            }
                        },
                        modifier = Modifier.shadow(elevation = 4.dp)
                    )
                }
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            if (uiState.errorMessage.isNullOrEmpty()) {
                if (uiState.filteredMovies.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No movies found.",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        state = listState,
                        modifier = Modifier.padding(innerPadding)
                            .padding(horizontal = 16.dp)
                            .padding(top = 30.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            uiState.filteredMovies
                        ) { movie ->
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = uiState.errorMessage
                                ?: "Something went wrong, Try again after sometime.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = {
                                viewModel.getTrendingMovies()
                            }
                        ) {
                            Text(text = "Retry")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun MovieListScreenPreview() {
    AtlysAssignmentTheme {
        MovieListScreen(
            navigateToMovieDetail = {}
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MovieListScreenDarkPreview() {
    AtlysAssignmentTheme {
        MovieListScreen(
            navigateToMovieDetail = {}
        )
    }
}