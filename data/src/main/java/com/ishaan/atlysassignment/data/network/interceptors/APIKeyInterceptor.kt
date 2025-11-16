package com.ishaan.atlysassignment.data.network.interceptors

import com.ishaan.atlysassignment.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

// Interceptor that automatically injects the API key and default query params into every network request
class APIKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Capture the original outgoing request
        val originalRequest = chain.request()
        // Extract the URL so we can append required query parameters
        val originalUrl = originalRequest.url
        // Build a new URL with API key and language appended
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", "en-US")
            .build()
        // Create a new request with the modified URL
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        // Proceed with the updated request through the chain
        return chain.proceed(newRequest)
    }
}