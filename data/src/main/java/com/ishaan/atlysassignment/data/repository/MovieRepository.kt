package com.ishaan.atlysassignment.data.repository

import com.ishaan.atlysassignment.data.models.Movie

interface MovieRepository {
    // Fetch trending movies for a specific page. The implementation may hit network or fallback to cache.
    suspend fun getAllTrendingMovies(page: Int): List<Movie>
}