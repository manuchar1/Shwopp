package com.tbcacademy.shwop.app

import android.app.Application
import android.content.Context
import com.tbcacademy.shwop.utils.DataStore
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Shwop : Application() {
    override fun onCreate() {
        super.onCreate()
        DataStore.initialize(this, getSharedPreferences("_sp_", Context.MODE_PRIVATE))
    }
}