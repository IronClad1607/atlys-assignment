package com.ishaan.atlysassignment.data.db

import androidx.room.TypeConverter

class GenreConverters {

    @TypeConverter
    fun fromList(genreIds: List<Int>): String {
        return genreIds.joinToString(",")
    }

    @TypeConverter
    fun toList(genreIdsString: String): List<Int> {
        if (genreIdsString.isEmpty()) {
            return emptyList()
        }
        return genreIdsString.split(",").map { it.toInt() }
    }
}