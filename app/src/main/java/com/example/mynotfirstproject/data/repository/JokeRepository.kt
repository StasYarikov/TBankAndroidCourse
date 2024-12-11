package com.example.mynotfirstproject.data.repository

import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.domain.entity.JokeTypes
import com.example.mynotfirstproject.data.datasource.remote.RemoteDataSource
import com.example.mynotfirstproject.data.datasource.local.JokeDao
import com.example.mynotfirstproject.data.datasource.local.LocalDataSource
import com.example.mynotfirstproject.data.datasource.remote.NetworkJokeDao
import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes
import com.example.mynotfirstproject.domain.repository.JokesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class JokeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    ) : JokesRepository {

    val generator = JokeGenerator

    override suspend fun getJokes(): List<JokeTypes> {
        val jokesList: List<Jokes> = localDataSource.getAllJokes().first()
        val jokeTypesList: List<JokeTypes> = jokesList.map { JokeTypes.MyJokes(it) }
        return jokeTypesList
    }

    override suspend fun loadMoreJokes(): JokeApiResponse {
        return remoteDataSource.getJokes()
    }

    override suspend fun generateJokes() {
        localDataSource.insertAll(generator.generateJokes())
    }

    override suspend fun addJoke(joke: JokeTypes) {
        when (joke) {
            is JokeTypes.MyJokes -> localDataSource.insert(joke.data)
            is JokeTypes.JokesFromNetwork -> remoteDataSource.insert(joke.data)
        }
    }

    override suspend fun addJokes(jokes: List<JokeTypes>) {
        jokes.forEach {
            when (it) {
                is JokeTypes.MyJokes -> localDataSource.insert(it.data)
                is JokeTypes.JokesFromNetwork -> remoteDataSource.insert(it.data)
            }
        }
    }

    override suspend fun deleteJoke(jokeId: Int) {
        localDataSource.deleteJokeById(jokeId)
    }

    override suspend fun getJokeById(jokeId: Int): JokeTypes {
        val joke = localDataSource.getJokeById(jokeId).first()
        if (joke != null) return JokeTypes.MyJokes(joke)
        else {
            return remoteDataSource.getJokeById(jokeId).first()!!.let { JokeTypes.JokesFromNetwork(it) }
        }
    }

    override suspend fun deleteNetworkJoke(jokeId: Int) {
        remoteDataSource.deleteNetworkJokeById(jokeId)
    }

    override suspend fun deleteAllJokes() {
        localDataSource.deleteAllJokes()
        remoteDataSource.deleteAllJokes()
    }

    private val CACHE_EXPIRATION_TIME = 24 * 60 * 60 * 1000

    override suspend fun getCacheJokes(): List<JokeTypes> {
        val currentTime = System.currentTimeMillis()

        val currentCache: List<NetworkJokes> = remoteDataSource.getAllNetworkJokes().first().toList()

        if (currentCache.isNotEmpty()) {
            val lastUpdate = currentCache.first().timestamp
            if (currentTime - lastUpdate <= CACHE_EXPIRATION_TIME) {

                return currentCache.map {
                    it.label = "From cache"
                    JokeTypes.JokesFromNetwork(it)
                }
            }
            else {
                remoteDataSource.getAllNetworkJokes()
            }
        }
        return emptyList()
    }
}
