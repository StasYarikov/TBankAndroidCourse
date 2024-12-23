package com.example.mynotfirstproject.di.module

import android.content.Context
import androidx.room.Room
import com.example.mynotfirstproject.data.datasource.db.AppDatabase
import com.example.mynotfirstproject.data.datasource.db.dao.JokeDao
import com.example.mynotfirstproject.data.datasource.db.dao.NetworkJokeDao
import com.example.mynotfirstproject.data.datasource.service.JokeApiService
import com.example.mynotfirstproject.data.jokeGenerator.JokeGenerator
import com.example.mynotfirstproject.data.jokeGenerator.JokeGeneratorImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType.toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : JokeApiService {
        return retrofit.create(JokeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideJokeDao(database: AppDatabase): JokeDao {
        return database.jokeDao()
    }

    @Provides
    @Singleton
    fun provideNetworkJokeDao(database: AppDatabase): NetworkJokeDao {
        return database.networkDao()
    }

    @Provides
    @Singleton
    fun provideJokeGenerator(): JokeGenerator {
        return JokeGeneratorImpl
    }

    private companion object {

        private const val BASE_URL = "https://v2.jokeapi.dev/"
        private const val DB_NAME = "app_database"
        private const val contentType = "application/json"

        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        private val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
}