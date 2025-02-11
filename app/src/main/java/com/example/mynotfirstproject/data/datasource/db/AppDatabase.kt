package com.example.mynotfirstproject.data.datasource.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotfirstproject.data.datasource.db.dao.JokeDao
import com.example.mynotfirstproject.data.datasource.db.dao.NetworkJokeDao
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes

@Database(entities = [NetworkJokes::class, Jokes::class], version = 11)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jokeDao(): JokeDao
    abstract fun networkDao(): NetworkJokeDao
}