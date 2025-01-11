package com.sumplier.app.app

import android.app.Application
import com.sumplier.app.data.DataStorage

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Start data storage
        DataStorage.init(this)
    }
}