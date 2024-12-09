package com.example.mynotfirstproject.data.repository

import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeTypes
import com.example.mynotfirstproject.data.db.JokeDao
import com.example.mynotfirstproject.data.db.NetworkJokeDao
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.data.entity.NetworkJokes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class JokeRepository(
    private val jokeDao: JokeDao,
    private val networkDao: NetworkJokeDao
    ) {

    val generator = JokeGenerator

    fun getJokes(): Flow<List<Jokes>> = jokeDao.getAllJokes()

    suspend fun generateJokes() {
        jokeDao.insertAll(JokeGenerator.generateJokes())
    }

    suspend fun addNetworkJoke(networkJokes: NetworkJokes) {
        networkDao.insert(networkJokes)
    }

    suspend fun addJoke(joke: Jokes) {
        jokeDao.insert(joke)
    }

    suspend fun addJokes(networkJokes: MutableList<NetworkJokes>) {
        networkDao.insertAllFromNetwork(networkJokes)
    }

    suspend fun deleteJoke(jokeId: Int) {
        jokeDao.deleteJokeById(jokeId)
    }

    suspend fun getJokeById(jokeId: Int): JokeTypes {
        val joke = jokeDao.getJokeById(jokeId).first()
        if (joke != null) return JokeTypes.MyJokes(joke)
        else {
            return networkDao.getJokeById(jokeId).first()!!.let { JokeTypes.JokesFromNetwork(it) }
        }
    }

    suspend fun deleteNetworkJoke(jokeId: Int) {
        networkDao.deleteNetworkJokeById(jokeId)
    }

    suspend fun deleteAllJokes() {
        jokeDao.deleteAllJokes()
        networkDao.deleteAllJokes()
    }

    private val CACHE_EXPIRATION_TIME = 24 * 60 * 60 * 1000

    suspend fun getCacheJokes(): List<NetworkJokes> {
        val currentTime = System.currentTimeMillis()

        val currentCache: List<NetworkJokes> = networkDao.getAllNetworkJokes().first().toList()

        if (currentCache.isNotEmpty()) {
            val lastUpdate = currentCache.first().timestamp
            if (currentTime - lastUpdate <= CACHE_EXPIRATION_TIME) {

                return currentCache.map {
                    it.label = "From cache"
                    it
                }
            }
            else {
                networkDao.getAllNetworkJokes()
            }
        }
        return emptyList()
    }
}
