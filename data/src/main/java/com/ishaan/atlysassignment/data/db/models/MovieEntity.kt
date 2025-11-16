package com.ishaan.atlysassignment.data.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieEntity(
    @ColumnInfo("adult")
    val adult: Boolean,
    @ColumnInfo("backdrop_path")
    val backdropPath: String,
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("original_title")
    val originalTitle: String,
    @ColumnInfo("overview")
    val overview: String,
    @ColumnInfo("poster_path")
    val posterPath: String,
    @ColumnInfo("media_type")
    val mediaType: String,
    @ColumnInfo("original_language")
    val originalLanguage: String,
    @ColumnInfo("genre_ids")
    val genreIds: List<Int>,
    @ColumnInfo("popularity")
    val popularity: Double,
    @ColumnInfo("release_date")
    val releaseDate: String,
    @ColumnInfo("video")
    val video: Boolean,
    @ColumnInfo("vote_average")
    val voteAverage: Double,
    @ColumnInfo("vote_count")
    val voteCount: Int
)