package com.ishaan.atlysassignment.data.network

import com.ishaan.atlysassignment.data.models.GetTrendingMoviesResponse
import com.ishaan.atlysassignment.data.network.call_adapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("trending/movie/week")
    suspend fun getAllTrendingMovies(
        @Query("page") page: Int
    ): NetworkResponse<GetTrendingMoviesResponse>
}