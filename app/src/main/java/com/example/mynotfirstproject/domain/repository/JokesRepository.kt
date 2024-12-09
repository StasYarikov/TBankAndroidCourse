package com.example.mynotfirstproject.domain.repository

import com.example.mynotfirstproject.domain.entity.JokeItem
import kotlinx.coroutines.flow.Flow

interface JokesRepository {

    fun getJokes(): Flow<List<JokeItem>>

    suspend fun generateJokes()

    suspend fun addJoke(jokeItem: JokeItem)

    suspend fun addJokes(jokes: List<JokeItem>)

    suspend fun getJokeById(jokeId: Int): JokeItem

    suspend fun deleteJoke(jokeId: Int)

    suspend fun deleteAllJokes()

    suspend fun deleteJokeById(jokeId: Int)


}