package com.ishaan.atlysassignment.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ishaan.atlysassignment.data.db.models.MovieEntity

@Dao
interface MovieDao {

    @Insert
    suspend fun insertAllMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies_table")
    suspend fun getAllTrendingMovies(): List<MovieEntity>
}