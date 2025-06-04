package com.example.mova

import android.app.Application
import com.example.mova.data.source.remote.network.AuthTokenProvider

class MovaApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AuthTokenProvider.init(this)
    }
}