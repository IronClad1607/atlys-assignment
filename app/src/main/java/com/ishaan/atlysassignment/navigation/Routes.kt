package com.ishaan.atlysassignment.navigation

import com.ishaan.atlysassignment.features.movie_detail.data.MovieDetailsArgs
import kotlinx.serialization.Serializable

@Serializable
object SplashScreenRoute

@Serializable
object MovieListScreenRoute

@Serializable
data class MovieDetailScreenRoute(
    val movieDetailsArgs: MovieDetailsArgs
)