package com.example.mynotfirstproject.data.datasource.db.implementations

import com.example.mynotfirstproject.data.datasource.db.dao.JokeDao
import com.example.mynotfirstproject.data.datasource.db.interfaces.LocalDataSource
import com.example.mynotfirstproject.data.entity.Jokes
import kotlinx.coroutines.flow.Flow

class LocalDataSourceImpl(
    private val jokeDao: JokeDao
) : LocalDataSource {
    override suspend fun insertAll(jokes: List<Jokes>) {
        jokeDao.insertAll(jokes)
    }

    override suspend fun insert(joke: Jokes) {
        jokeDao.insert(joke)
    }

    override suspend fun deleteJokeById(jokeId: Int) {
        jokeDao.deleteJokeById(jokeId)
    }

    override fun getJokeById(jokeId: Int): Flow<Jokes?> {
        return jokeDao.getJokeById(jokeId)
    }

    override fun getAllJokes(): Flow<List<Jokes>> {
        return jokeDao.getAllJokes()
    }

    override suspend fun deleteAllJokes() {
        jokeDao.deleteAllJokes()
    }

}