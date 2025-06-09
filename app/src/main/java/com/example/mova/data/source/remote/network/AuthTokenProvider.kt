package com.example.mova.data.source.remote.network

import android.content.Context
import com.example.mova.data.source.local.DataStoreManager
import kotlinx.coroutines.runBlocking

object AuthTokenProvider {
    private lateinit var dataStoreManager: DataStoreManager

    fun init(context: Context) {
        dataStoreManager = DataStoreManager(context)
    }

    fun getAccessToken(): String? = runBlocking {
        dataStoreManager.getAccessToken()
    }

    fun getRefreshToken(): String? = runBlocking {
        dataStoreManager.getRefreshToken()
    }

    fun saveAccessToken(token: String) = runBlocking {
        dataStoreManager.saveAccessToken(token)
    }

    fun saveRefreshToken(token: String) = runBlocking {
        dataStoreManager.saveRefreshToken(token)
    }
}