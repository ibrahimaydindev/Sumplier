package com.sumplier.app.app

import android.app.Application
import com.sumplier.app.data.database.PreferencesHelper
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Start data storage
        PreferencesHelper.init(this)
    }
}