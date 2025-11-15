package com.ishaan.atlysassignment.data.repository

import com.ishaan.atlysassignment.data.models.GetTrendingMoviesResponse
import com.ishaan.atlysassignment.data.network.call_adapter.NetworkResponse

interface MovieRepository {
    suspend fun getAllTrendingMovies(page: Int): NetworkResponse<GetTrendingMoviesResponse>
}