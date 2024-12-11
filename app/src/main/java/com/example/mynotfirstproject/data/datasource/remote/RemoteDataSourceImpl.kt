package com.example.mynotfirstproject.data.datasource.remote

import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.NetworkJokes
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val api: JokeApiService,
    private val networkJokeDao: NetworkJokeDao
) : RemoteDataSource {

    override suspend fun getJokes(): JokeApiResponse {
        return api.getJokes()
    }

    override suspend fun insertAllFromNetwork(networkJokes: List<NetworkJokes>) {
        networkJokeDao.insertAllFromNetwork(networkJokes)
    }

    override suspend fun insert(networkJokes: NetworkJokes) {
        networkJokeDao.insert(networkJokes)
    }

    override suspend fun deleteNetworkJokeById(jokeId: Int) {
        networkJokeDao.deleteNetworkJokeById(jokeId)
    }

    override fun getJokeById(jokeId: Int): Flow<NetworkJokes?> {
        return networkJokeDao.getJokeById(jokeId)
    }

    override fun getAllNetworkJokes(): Flow<List<NetworkJokes>> {
        return networkJokeDao.getAllNetworkJokes()
    }

    override suspend fun deleteAllJokes() {
        networkJokeDao.deleteAllJokes()
    }
}