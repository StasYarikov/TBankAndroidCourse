package com.example.mynotfirstproject

import android.app.Application
import com.example.mynotfirstproject.data.datasource.db.AppDatabase
import com.example.mynotfirstproject.di.AppComponent
import com.example.mynotfirstproject.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}