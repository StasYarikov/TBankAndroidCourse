package com.example.mynotfirstproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.db.JokeDao
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class JokeRepository(private val jokeDao: JokeDao) {

    val generator = JokeGenerator

    fun getJokes(): Flow<List<Joke>> = jokeDao.getAllJokes()

    suspend fun generateJokes() {
        jokeDao.insertAll(generator.generateJokes())
    }

    suspend fun addJoke(joke: Joke) {
        jokeDao.insert(joke)
    }

    suspend fun addJokes(jokes: MutableList<Joke>) {
        jokeDao.insertAll(jokes)
    }

    suspend fun deleteJoke(jokeId: Int) {
        jokeDao.deleteJokeById(jokeId)
    }

    suspend fun loadJokesWithDelay() : List<Joke> {
        return jokeDao.getAllJokes().last()
    }
}
