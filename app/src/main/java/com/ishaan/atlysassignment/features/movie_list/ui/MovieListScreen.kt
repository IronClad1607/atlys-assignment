package com.ishaan.atlysassignment.features.movie_list.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
            TopAppBar(
                title = {
                    Text(
                        text = "Trending Movies"
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                modifier = Modifier.shadow(elevation = 4.dp)
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        modifier = modifier
    ) { innerPadding ->
        if (uiState.isLoading) {
            LoadingIndicator()
        } else {
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