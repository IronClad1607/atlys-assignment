package com.ishaan.atlysassignment.data.repository

import android.util.Log
import com.ishaan.atlysassignment.data.db.MovieDao
import com.ishaan.atlysassignment.data.models.GetTrendingMoviesResponse
import com.ishaan.atlysassignment.data.models.Movie
import com.ishaan.atlysassignment.data.network.APIService
import com.ishaan.atlysassignment.data.network.call_adapter.NetworkResponse
import com.ishaan.atlysassignment.data.util.toMovie
import com.ishaan.atlysassignment.data.util.toMovieEntity
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: APIService,
    private val movieDao: MovieDao
) : MovieRepository {

    companion object {
        const val TAG = "MovieRepository"
    }

    private suspend fun getCachedMovies(): List<Movie> {
        Log.d(TAG, "Loading movies from local cache.")
        // We can map directly. If the list is empty, map() will correctly return an empty list.
        return movieDao.getAllTrendingMovies().map { it.toMovie() }
    }

    override suspend fun getAllTrendingMovies(page: Int): List<Movie> {
        return try {
            when (val networkResponse = apiService.getAllTrendingMovies(page)) {

                is NetworkResponse.Error -> {
                    Log.e(TAG, "API failed, loading from cache.")
                    getCachedMovies()
                }

                is NetworkResponse.Success<GetTrendingMoviesResponse> -> {
                    val moviesDtoList = networkResponse.body.movies

                    if (moviesDtoList.isNotEmpty()) {
                        Log.d(TAG, "Successfully fetched ${moviesDtoList.size} movies from API.")

                        // Clear cache only on the first page
                        if (page == 1) {
                            movieDao.clearAllMovies()
                        }

                        // Convert DTOs -> Entities
                        val movieEntities = moviesDtoList.map { it.toMovieEntity() }

                        // Save Entities to DB
                        movieDao.insertAllMovies(movieEntities)

                        movieEntities.map { it.toMovie() }
                    } else {
                        // API was successful but returned no movies (e.g., end of pagination)
                        Log.d(TAG, "API success but no movies returned, loading from cache.")
                        getCachedMovies()
                    }
                }
            }
        } catch (e: Exception) {
            // Catch *all* exceptions (network, parsing, DB, etc.)
            // and fall back to cache for a robust offline-first experience.
            Log.e(TAG, "Exception in getAllTrendingMovies, loading from cache", e) // Pass exception for full stack trace
            getCachedMovies() // Consistent fallback
        }
    }
}