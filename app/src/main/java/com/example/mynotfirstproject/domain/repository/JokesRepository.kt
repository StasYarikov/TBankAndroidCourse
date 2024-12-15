package com.example.mynotfirstproject.domain.repository

import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes
import com.example.mynotfirstproject.domain.entity.JokeItem
import kotlinx.coroutines.flow.Flow

interface JokesRepository {

    suspend fun getJokes(): List<JokeItem>

    suspend fun loadMoreJokes(): List<JokeItem>

    suspend fun generateJokes()

    suspend fun addJoke(joke: JokeItem)

    suspend fun addJokes(jokes: List<JokeItem>)

    suspend fun getJokeById(jokeId: Int): JokeItem

    suspend fun deleteJoke(jokeId: Int)

    suspend fun deleteNetworkJoke(jokeId: Int)

    suspend fun deleteAllJokes()

    suspend fun getCacheJokes(): List<JokeItem>

}