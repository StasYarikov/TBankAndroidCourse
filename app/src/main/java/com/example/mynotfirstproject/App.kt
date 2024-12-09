package com.example.mynotfirstproject

import android.app.Application
import com.example.mynotfirstproject.data.db.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
    }
}