package com.example.mova.data.source.network

import com.example.mova.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TMDBClient {
    private val TMDBokHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    val api: TMDBService by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(TMDBokHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TMDBService::class.java)
    }
}