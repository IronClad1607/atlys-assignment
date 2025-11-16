package com.ishaan.atlysassignment.features.movie_detail.data

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Serializable
data class MovieDetailsArgs(
    val title: String,
    val overview: String,
    val backdropPath: String
)


class MovieDetailsArgsNavType : NavType<MovieDetailsArgs>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): MovieDetailsArgs? {
        // Get the JSON string from the bundle
        // No decoding needed here, bundle stores the raw string
        return bundle.getString(key)?.let { jsonString ->
            Json.decodeFromString<MovieDetailsArgs>(jsonString)
        }
    }

    override fun parseValue(value: String): MovieDetailsArgs {
        // The 'value' is from the route string, so it's URL-encoded.
        // First, decode the string.
        val decodedJsonString = URLDecoder.decode(value, StandardCharsets.UTF_8.name())
        // Then, parse the JSON string.
        return Json.decodeFromString<MovieDetailsArgs>(decodedJsonString)
    }

    override fun put(bundle: Bundle, key: String, value: MovieDetailsArgs) {
        // Put the JSON string into the bundle
        // No encoding needed for the bundle
        val jsonString = Json.encodeToString(value)
        bundle.putString(key, jsonString)
    }

    override fun serializeAsValue(value: MovieDetailsArgs): String {
        // Serialize the object to a JSON string
        val jsonString = Json.encodeToString(value)
        // URL-encode the JSON string to make it safe for the route
        return URLEncoder.encode(jsonString, StandardCharsets.UTF_8.name())
    }
}