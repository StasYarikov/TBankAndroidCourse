package com.example.mynotfirstproject.domain.repository

import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes
import com.example.mynotfirstproject.domain.entity.JokeTypes
import kotlinx.coroutines.flow.Flow

interface JokesRepository {

    suspend fun getJokes(): List<JokeTypes>

    suspend fun loadMoreJokes(): JokeApiResponse

    suspend fun generateJokes()

    suspend fun addJoke(joke: JokeTypes)

    suspend fun addJokes(jokes: List<JokeTypes>)

    suspend fun getJokeById(jokeId: Int): JokeTypes

    suspend fun deleteJoke(jokeId: Int)

    suspend fun deleteNetworkJoke(jokeId: Int)

    suspend fun deleteAllJokes()

    suspend fun getCacheJokes(): List<JokeTypes>

}