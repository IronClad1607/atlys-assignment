package com.ishaan.atlysassignment.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class APIKeyInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("api_key", "d5b568462e39f02e011bb612583ead1e")
            .addQueryParameter("language", "en-US")
            .build()
        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}