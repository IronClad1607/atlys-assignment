package com.ishaan.atlysassignment.features.movie_list.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ishaan.atlysassignment.base.BaseViewModel
import com.ishaan.atlysassignment.data.repository.MovieRepository
import com.ishaan.atlysassignment.features.movie_list.data.MovieListUIEvents
import com.ishaan.atlysassignment.features.movie_list.data.MovieListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel<
        MovieListUIState,
        MovieListUIEvents
        >(MovieListUIState()) {

    companion object {
        const val TAG = "MovieListViewModel"
    }

    // Tracks the current pagination page for fetching trending movies
    var currentPage = 1

    fun getTrendingMovies() {
        viewModelScope.launch {
            Log.d(
                TAG,
                "Fetching trending movies for page: $currentPage"
            )
            val currentState = _uiState.value
            Log.d(
                TAG,
                "Current UI State: isLoading=${currentState.isLoading}"
            )
            if (currentState.isLoading) {
                Log.d(
                    TAG,
                    "Fetch aborted because loading is already in progress"
                )
                return@launch
            }

            safeUpdateState { oldState ->
                oldState.copy(isLoading = true, errorMessage = null)
            }

            Log.d(TAG, "Calling repository to get trending movies...")
            val movies = repository.getAllTrendingMovies(currentPage)
            Log.d(TAG, "Repository returned ${movies.size} movies")

            Log.d(
                TAG,
                "Movies fetched successfully, updating UI state"
            )
            if (movies.isNotEmpty()) {
                safeUpdateState { oldState ->
                    oldState.copy(
                        isLoading = false,
                        movies = movies,
                        filteredMovies = movies,
                        errorMessage = null
                    )
                }
            } else {
                Log.e(
                    TAG,
                    "Movie list is empty. Showing error message to user."
                )
                safeUpdateState { oldState ->
                    oldState.copy(
                        isLoading = false,
                        errorMessage = "Something went wrong, Try again after sometime."
                    )
                }
            }
        }
    }

    // Toggles the visibility of the search bar and resets search state when closed
    fun onSearchIconClicked(open: Boolean) {
        Log.d(TAG, "Search icon clicked. Open state: $open")
        safeUpdateState { olState ->
            if (!open) {
                olState.copy(
                    isSearchOpen = false,
                    searchText = "",
                    filteredMovies = olState.movies
                )
            } else {
                olState.copy(
                    isSearchOpen = true
                )
            }
        }
    }

    // Handles search text updates and filters movies based on user input
    fun onSearchUpdate(searchText: String) {
        Log.d(TAG, "Search text updated: $searchText")
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
        Log.d(TAG, "Filtered movies count: ${searchedMovies.size}")

        safeUpdateState { oldState ->
            oldState.copy(
                isLoading = false,
                filteredMovies = searchedMovies,
            )
        }
    }
}