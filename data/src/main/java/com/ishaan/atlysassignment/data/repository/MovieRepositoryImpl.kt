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

    // Load movies stored locally in the Room database as a fallback or offline-first strategy
    private suspend fun getCachedMovies(): List<Movie> {
        Log.d(TAG, "Loading movies from local cache.")
        Log.d(TAG, "Cached movies count: ${movieDao.getAllTrendingMovies().size}")
        // We can map directly. If the list is empty, map() will correctly return an empty list.
        return movieDao.getAllTrendingMovies().map { it.toMovie() }
    }

    // Fetch trending movies from API; fallback to cache on failure. Also handles pagination + local DB sync.
    override suspend fun getAllTrendingMovies(page: Int): List<Movie> {
        Log.d(TAG, "Requesting trending movies for page: $page")
        return try {
            when (val networkResponse = apiService.getAllTrendingMovies(page)) {

                is NetworkResponse.Error -> {
                    Log.e(TAG, "API failed, loading from cache.")
                    Log.e(TAG, "Network error details: ${networkResponse.body.errorMessage}")
                    getCachedMovies()
                }

                is NetworkResponse.Success<GetTrendingMoviesResponse> -> {
                    val moviesDtoList = networkResponse.body.movies

                    if (moviesDtoList.isNotEmpty()) {
                        Log.d(TAG, "Successfully fetched ${moviesDtoList.size} movies from API.")
                        Log.d(TAG, "Mapping DTOs to MovieEntity list...")

                        // Clear cache only on the first page
                        if (page == 1) {
                            movieDao.clearAllMovies()
                        }

                        // Convert DTOs -> Entities
                        val movieEntities = moviesDtoList.map { it.toMovieEntity() }

                        // Save Entities to DB
                        movieDao.insertAllMovies(movieEntities)
                        Log.d(TAG, "Saved ${movieEntities.size} movies to local database.")

                        movieEntities.map { it.toMovie() }
                    } else {
                        // API was successful but returned no movies (e.g., end of pagination)
                        Log.d(TAG, "API success but no movies returned, loading from cache.")
                        Log.d(TAG, "API returned empty list. Possibly last page reached.")
                        getCachedMovies()
                    }
                }
            }
        } catch (e: Exception) {
            // Catch *all* exceptions (network, parsing, DB, etc.)
            // and fall back to cache for a robust offline-first experience.
            Log.e(
                TAG,
                "Exception in getAllTrendingMovies, loading from cache",
                e
            ) // Pass exception for full stack trace
            Log.e(TAG, "Exception message: ${e.message}")
            getCachedMovies() // Consistent fallback
        }
    }
}