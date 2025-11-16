package com.ishaan.atlysassignment.data.util

import com.ishaan.atlysassignment.data.db.models.MovieEntity
import com.ishaan.atlysassignment.data.models.Movie


// Extension function to convert a Movie domain model into a MovieEntity for local database storage
fun Movie.toMovieEntity(): MovieEntity {
    // Map all Movie fields to their corresponding MovieEntity fields
    return MovieEntity(
        adult = adult,
        backdropPath = backdropPath,
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        mediaType = mediaType,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        popularity = popularity,
        releaseDate = releaseDate,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

// Extension function to convert a MovieEntity from the database back into a Movie domain model
fun MovieEntity.toMovie(): Movie {
    // Map all MovieEntity fields back to the Movie model
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        posterPath = posterPath,
        mediaType = mediaType,
        originalLanguage = originalLanguage,
        genreIds = genreIds,
        popularity = popularity,
        releaseDate = releaseDate,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}