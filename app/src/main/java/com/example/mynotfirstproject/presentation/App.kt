package com.example.mynotfirstproject.presentation

import android.app.Application
import com.example.mynotfirstproject.data.datasource.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
    }
}