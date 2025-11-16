package com.ishaan.atlysassignment.data.repository

import com.ishaan.atlysassignment.data.models.Movie

interface MovieRepository {
    suspend fun getAllTrendingMovies(page: Int): List<Movie>
}