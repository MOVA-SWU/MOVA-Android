package com.example.mova.data.source.remote.network

import android.util.Log
import com.example.mova.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val token = AuthTokenProvider.getAccessToken()
                Log.d("AuthInterceptor", "AccessToken = $token")
                val request = chain.request().newBuilder()
                    .apply {
                        token?.let {
                            addHeader("Authorization", "Bearer $it")
                        }
                    }
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val TMDBokHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val tmdbRetrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(TMDBokHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitService: RetrofitService by lazy {
        retrofit.create(RetrofitService::class.java)
    }

    val tmdbService: TMDBService by lazy {
        tmdbRetrofit.create(TMDBService::class.java)
    }
}