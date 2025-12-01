package com.example.mova.di

import com.example.mova.BuildConfig
import com.example.mova.data.model.request.RefreshTokenRequest
import com.example.mova.data.source.remote.network.AuthTokenProvider
import com.example.mova.data.source.remote.network.RetrofitService
import com.example.mova.data.source.remote.network.TMDBService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @Named("authInterceptor")
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        var request = chain.request()

        val accessToken = AuthTokenProvider.getAccessToken()
        val refreshToken = AuthTokenProvider.getRefreshToken()

        request = request.newBuilder().apply {
            accessToken?.let { addHeader("Authorization", "Bearer $it") }
        }.build()

        val response = chain.proceed(request)

        if (response.code == 401 && refreshToken != null) {
            response.close()

            val refreshResponse = try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(RetrofitService::class.java)

                val newToken = runBlocking {
                    service.postRefreshToken(RefreshTokenRequest(refreshToken))
                }

                AuthTokenProvider.saveAccessToken(newToken.accessToken)
                AuthTokenProvider.saveRefreshToken(newToken.refreshToken)

                val newRequest = request.newBuilder()
                    .removeHeader("Authorization")
                    .addHeader("Authorization", "Bearer ${newToken.accessToken}")
                    .build()

                chain.proceed(newRequest)
            } catch (e: Exception) {
                e.printStackTrace()
                return@Interceptor response
            }
            return@Interceptor refreshResponse
        }
        return@Interceptor response
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @Named("baseOkHttpClient")
    fun provideOkHttpClient(
        @Named("authInterceptor") authInterceptor: Interceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @Named("tmdbOkHttpClient")
    fun provideTMDBOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    @Named("baseRetrofit")
    fun provideRetrofit(
        @Named("baseOkHttpClient") okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("tmdbRetrofit")
    fun provideTMDBRetrofit(
        @Named("tmdbOkHttpClient") tmdbOkHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.TMDB_BASE_URL)
            .client(tmdbOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideRetrofitService(
        @Named("baseRetrofit") retrofit: Retrofit
    ): RetrofitService = retrofit.create(RetrofitService::class.java)

    @Provides
    @Singleton
    fun provideTMDBService(
        @Named("tmdbRetrofit") tmdbRetrofit: Retrofit
    ): TMDBService = tmdbRetrofit.create(TMDBService::class.java)
}
