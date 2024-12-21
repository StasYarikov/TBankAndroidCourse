package com.example.mynotfirstproject.data.datasource.db.implementations

import com.example.mynotfirstproject.data.datasource.db.dao.NetworkJokeDao
import com.example.mynotfirstproject.data.datasource.db.interfaces.RemoteDataSource
import com.example.mynotfirstproject.data.datasource.service.JokeApiService
import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.NetworkJokes
import kotlinx.coroutines.flow.Flow

class RemoteDataSourceImpl(
    private val networkJokeDao: NetworkJokeDao
) : RemoteDataSource {

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