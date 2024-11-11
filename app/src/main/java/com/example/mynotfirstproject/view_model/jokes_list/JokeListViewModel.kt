package com.example.mynotfirstproject.view_model.jokes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator

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
}