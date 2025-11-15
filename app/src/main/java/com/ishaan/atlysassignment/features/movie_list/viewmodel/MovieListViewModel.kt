package com.ishaan.atlysassignment.features.movie_list.viewmodel

import com.ishaan.atlysassignment.base.BaseViewModel
import com.ishaan.atlysassignment.data.repository.MovieRepository
import com.ishaan.atlysassignment.features.movie_list.data.MovieListUIEvents
import com.ishaan.atlysassignment.features.movie_list.data.MovieListUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repositoryImpl: MovieRepository
) : BaseViewModel<
        MovieListUIState,
        MovieListUIEvents
        >() {
}