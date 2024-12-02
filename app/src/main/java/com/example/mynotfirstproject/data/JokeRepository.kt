package com.example.mynotfirstproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JokeRepository {
    private val jokesLiveData = MutableLiveData<List<Joke>>()
    private val selectedJokeLiveData = MutableLiveData<Joke>()

    val generator = JokeGenerator

    fun getJokes(): LiveData<List<Joke>> = jokesLiveData

    fun generateJokes() {
        jokesLiveData.value = generator.generateJokes()
    }

    fun addJoke(joke: Joke) {
        val updatedList = jokesLiveData.value.orEmpty().toMutableList()
        updatedList.add(joke)
        jokesLiveData.value = updatedList
    }

    fun addJokes(jokes: JokeApiResponse) {
        val updatedList = jokesLiveData.value.orEmpty().toMutableList()
        updatedList.addAll(jokes.jokes)
        jokesLiveData.value = updatedList
    }

    fun deleteJoke(jokeNumber: Int) {
        val updatedList = jokesLiveData.value.orEmpty().toMutableList()
        updatedList.removeAt(jokeNumber)
        jokesLiveData.value = updatedList
    }

    fun selectJoke(joke: Joke) {
        selectedJokeLiveData.value = joke
    }

    fun loadJokesWithDelay() {
        jokesLiveData.postValue(jokesLiveData.value)
    }

    fun getSelectedJoke(): LiveData<Joke> = selectedJokeLiveData
}
