package com.example.mova.data.source.network

import com.example.mova.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val TMDBokHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val tmdbRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(TMDBokHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val tmdbService: TMDBService by lazy {
        tmdbRetrofit.create(TMDBService::class.java)
    }
}