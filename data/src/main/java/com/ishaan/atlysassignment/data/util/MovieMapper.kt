package com.ishaan.atlysassignment.data.util

import com.ishaan.atlysassignment.data.db.models.MovieEntity
import com.ishaan.atlysassignment.data.models.Movie


fun Movie.toMovieEntity(): MovieEntity {
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

fun MovieEntity.toMovie(): Movie {
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