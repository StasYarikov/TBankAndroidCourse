package com.example.mynotfirstproject.data.datasource.db.interfaces

import com.example.mynotfirstproject.data.entity.Jokes
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun insertAll(jokes: List<Jokes>)

    suspend fun insert(joke: Jokes)

    suspend fun deleteJokeById(jokeId: Int)

    fun getJokeById(jokeId: Int): Flow<Jokes?>

    fun getAllJokes(): Flow<List<Jokes>>

    suspend fun deleteAllJokes()
}