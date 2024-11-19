package com.example.mynotfirstproject.view_model.jokes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JokeListViewModel: ViewModel() {

    private val _jokes = MutableLiveData<List<Joke>>()
    val jokes: LiveData<List<Joke>> = _jokes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun generateJokes() {
        _jokes.value = JokeGenerator.generateJokes()
    }

    fun showGeneratedData() {
        _jokes.value = JokeGenerator.data
    }

    fun addJoke(joke: Joke) {
        val updatedList = _jokes.value.orEmpty().toMutableList()
        updatedList.add(joke)
        _jokes.value = updatedList
    }

    fun deleteJoke(jokeNumber: Int) {
        val updatedList = _jokes.value.orEmpty().toMutableList()
        updatedList.removeAt(jokeNumber)
        _jokes.value = updatedList
    }

    fun loadJokesWithDelay() {
        viewModelScope.launch {
            delay(2000)
            _jokes.postValue(_jokes.value)
        }
    }
}