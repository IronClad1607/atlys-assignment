package com.ishaan.atlysassignment.features.movie_list.viewmodel

import androidx.lifecycle.viewModelScope
import com.ishaan.atlysassignment.base.BaseViewModel
import com.ishaan.atlysassignment.data.repository.MovieRepository
import com.ishaan.atlysassignment.features.movie_list.data.MovieListUIEvents
import com.ishaan.atlysassignment.features.movie_list.data.MovieListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel<
        MovieListUIState,
        MovieListUIEvents
        >(MovieListUIState()) {

    var currentPage = 1

    fun getTrendingMovies() {
        viewModelScope.launch {
            val currentState = _uiState.value
            if (currentState.isLoading) {
                return@launch
            }

            safeUpdateState { oldState ->
                oldState.copy(isLoading = true)
            }

            val movies = repository.getAllTrendingMovies(currentPage)

            if (movies.isNotEmpty()) {
                safeUpdateState { oldState ->
                    oldState.copy(
                        isLoading = false,
                        movies = movies,
                        filteredMovies = movies
                    )
                }
            } else {
                safeUpdateState { oldState ->
                    oldState.copy(
                        isLoading = false,
                        errorMessage = "Something went wrong, Try again after sometime."
                    )
                }
            }
        }
    }

    fun onSearchIconClicked(open: Boolean) {
        safeUpdateState { olState ->
            olState.copy(
                isSearchOpen = open
            )
        }
    }

    fun onSearchUpdate(searchText: String) {
        safeUpdateState { oldState ->
            oldState.copy(
                isLoading = true,
                searchText = searchText
            )
        }

        val currentState = _uiState.value
        val searchedMovies = if (searchText.isEmpty()) {
            currentState.movies
        } else {
            currentState.movies.filter { movie ->
                movie.title.contains(searchText, ignoreCase = true)
            }
        }

        safeUpdateState {oldState ->
            oldState.copy(
                isLoading = false,
                filteredMovies = searchedMovies,
            )
        }
    }
}