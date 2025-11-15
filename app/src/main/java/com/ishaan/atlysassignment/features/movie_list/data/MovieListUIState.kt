package com.ishaan.atlysassignment.features.movie_list.data

import com.ishaan.atlysassignment.data.models.Movie

data class MovieListUIState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val errorMessage: String? = null
)

sealed class MovieListUIEvents {

}