package com.example.mynotfirstproject.view_model.jokes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JokeListViewModel(
    private val repository: JokeRepository
): ViewModel() {

    val jokes: LiveData<List<Joke>> = repository.getJokes()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun generateJokes() {
        repository.generateJokes()
    }

    fun loadJokesWithDelay() {
        viewModelScope.launch {
            delay(2000)
            repository.loadJokesWithDelay()
        }
    }

    fun exceptionHandler() = CoroutineExceptionHandler { _, throwable ->
        handleError("Error")
    }

    fun loadMoreJokes() {
        viewModelScope.launch(exceptionHandler) {
            val response : JokeResponse = RetrofitInstance.jokeApiService.getJokes(amount = 10)
            repository.loadJokesWithDelay() = newJokes
            _loading.value = false
        }
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}