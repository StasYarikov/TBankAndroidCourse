package com.example.mynotfirstproject.data.repository

import com.example.mynotfirstproject.data.jokeGenerator.JokeGeneratorImpl
import com.example.mynotfirstproject.data.datasource.db.interfaces.RemoteDataSource
import com.example.mynotfirstproject.data.datasource.db.interfaces.LocalDataSource
import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes
import com.example.mynotfirstproject.data.mapper.JokeItemJokesMapper
import com.example.mynotfirstproject.data.mapper.JokeItemNetworkJokesMapper
import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.domain.repository.JokesRepository
import kotlinx.coroutines.flow.first

class JokeRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val jokeItemJokesMapper: JokeItemJokesMapper,
    private val jokeItemNetworkJokesMapper: JokeItemNetworkJokesMapper,
    ) : JokesRepository {

    private val generator = JokeGeneratorImpl

    override suspend fun getJokes(): List<JokeItem> {
        val jokesList: List<Jokes> = localDataSource.getAllJokes().first()
        return if (jokesList.isEmpty()) {
            remoteDataSource.getJokes().networkJokes.map { jokeItemNetworkJokesMapper.map(it) }
        } else jokesList.map {
            jokeItemJokesMapper.map(it)
        }
    }

    override suspend fun loadMoreJokes(): List<JokeItem> {
        return remoteDataSource.getJokes().networkJokes.map {
            jokeItemNetworkJokesMapper.map(it)
        }
    }

    override suspend fun generateJokes() {
        localDataSource.insertAll(generator.generateJokes())
    }

    override suspend fun addJoke(joke: JokeItem) {
        localDataSource.insert(jokeItemJokesMapper.mapToJokesFromJokeItem(joke))
    }

    override suspend fun addJokes(jokes: List<JokeItem>) {
        remoteDataSource.insertAllFromNetwork(
            jokes.map {
                jokeItemNetworkJokesMapper.mapToNetworkJokesFromJokeItem(it)
            }
        )
    }

    override suspend fun deleteJoke(jokeId: Int) {
        localDataSource.deleteJokeById(jokeId)
    }

    override suspend fun getJokeById(jokeId: Int): JokeItem {
        val joke = localDataSource.getJokeById(jokeId).first()
        if (joke != null) return jokeItemJokesMapper.map(joke)
        else {
            return jokeItemNetworkJokesMapper.map( remoteDataSource.getJokeById(jokeId).first()!! )
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

    override suspend fun getCacheJokes(): List<JokeItem> {
        val currentTime = System.currentTimeMillis()

        val currentCache: List<NetworkJokes> = remoteDataSource.getAllNetworkJokes().first().toList()

        if (currentCache.isNotEmpty()) {
            val lastUpdate = currentCache.first().timestamp
            if (currentTime - lastUpdate <= CACHE_EXPIRATION_TIME) {

                return currentCache.map {
                    jokeItemNetworkJokesMapper.map(it)
                }
            }
            else {
                remoteDataSource.deleteAllJokes()
            }
        }
        return emptyList()
    }
}
