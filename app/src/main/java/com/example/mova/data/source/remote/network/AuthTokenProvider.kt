package com.example.mova.data.source.remote.network

import android.content.Context
import com.example.mova.data.source.local.DataStoreManager
import kotlinx.coroutines.runBlocking

object AuthTokenProvider {
    // var accessToken: String? = null

    private lateinit var dataStoreManager: DataStoreManager

    fun init(context: Context) {
        dataStoreManager = DataStoreManager(context)
    }

    fun getAccessToken(): String? = runBlocking {
        dataStoreManager.getAccessToken()
    }
}