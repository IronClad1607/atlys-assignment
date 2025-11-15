package com.ishaan.atlysassignment.features.movie_list.repository

import com.ishaan.atlysassignment.data.models.GetTrendingMoviesResponse
import com.ishaan.atlysassignment.data.network.APIService
import com.ishaan.atlysassignment.data.network.call_adapter.NetworkResponse
import com.ishaan.atlysassignment.data.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: APIService
) : MovieRepository {
    override suspend fun getAllTrendingMovies(page: Int): NetworkResponse<GetTrendingMoviesResponse> {
        return apiService.getAllTrendingMovies(page)
    }
}