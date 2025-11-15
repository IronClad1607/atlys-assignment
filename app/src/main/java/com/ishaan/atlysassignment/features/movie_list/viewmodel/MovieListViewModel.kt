package com.ishaan.atlysassignment.features.movie_list.viewmodel

import android.util.Log
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

    fun getTrendingMovies(pageToFetch: Int) {
        viewModelScope.launch {
            when (val response = repository.getAllTrendingMovies(pageToFetch)) {
                is NetworkResponse.Error -> {
                    Log.e("PUI", "error: ${response.body.errorMessage}")
                }

                is NetworkResponse.Success<GetTrendingMoviesResponse> -> {
                    val movies = response.body.movies
                    Log.d("PUI", "movies: ${movies.size}")
                }
            }
        }
    }
}