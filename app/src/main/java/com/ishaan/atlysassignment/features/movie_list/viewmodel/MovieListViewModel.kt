package com.ishaan.atlysassignment.features.movie_list.viewmodel

import androidx.lifecycle.viewModelScope
import com.ishaan.atlysassignment.base.BaseViewModel
import com.ishaan.atlysassignment.data.models.GetTrendingMoviesResponse
import com.ishaan.atlysassignment.data.network.call_adapter.NetworkResponse
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

            when (val response = repository.getAllTrendingMovies(currentPage)) {
                is NetworkResponse.Error -> {
                    safeUpdateState { oldState ->
                        oldState.copy(
                            isLoading = false,
                            errorMessage = response.body.errorMessage
                        )
                    }
                }

                is NetworkResponse.Success<GetTrendingMoviesResponse> -> {
                    val movies = response.body.movies
                    safeUpdateState { oldState ->
                        if (movies.isNotEmpty()) {
                            oldState.copy(
                                isLoading = false,
                                movies = movies,
                            )
                        } else {
                            oldState.copy(
                                isLoading = false,
                                allMoviesLoaded = true
                            )
                        }
                    }
                }
            }
        }
    }
}