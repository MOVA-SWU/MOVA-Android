package com.example.mova.data.source.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    suspend fun saveAccessToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token
        }
    }

    suspend fun getAccessToken(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[ACCESS_TOKEN]
        }.first()
    }

    suspend fun saveRefreshToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = token
        }
    }

    suspend fun getRefreshToken(): String? {
        return context.dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN]
        }.first()
    }

    suspend fun clearTokens() {
        context.dataStore.edit { prefs ->
            prefs.remove(ACCESS_TOKEN)
            prefs.remove(REFRESH_TOKEN)
        }
    }
}