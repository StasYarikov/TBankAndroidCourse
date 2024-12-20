package com.example.mynotfirstproject.data.datasource.db.interfaces

import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.NetworkJokes
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getJokes(): JokeApiResponse

    suspend fun insertAllFromNetwork(networkJokes: List<NetworkJokes>)

    suspend fun insert(networkJokes: NetworkJokes)

    suspend fun deleteNetworkJokeById(jokeId: Int)

    fun getJokeById(jokeId: Int): Flow<NetworkJokes?>

    fun getAllNetworkJokes(): Flow<List<NetworkJokes>>

    suspend fun deleteAllJokes()
}