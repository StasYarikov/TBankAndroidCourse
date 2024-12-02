package com.example.mynotfirstproject.view_model.jokes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeApiResponse
import com.example.mynotfirstproject.data.JokeRepository
import com.example.mynotfirstproject.data.api.RetrofitInstance
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class JokeListViewModel(
    private val repository: JokeRepository
): ViewModel() {

    private val _jokesFlow = MutableStateFlow<List<Joke>>(emptyList())
    val jokesFlow: StateFlow<List<Joke>> get() = _jokesFlow

    private val mutableProgressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean> = mutableProgressLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    var loadingProcess: Boolean = false

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable.toString())
        mutableProgressLiveData.postValue(false)
    }

    fun generateJokes() {
        viewModelScope.launch {
            repository.generateJokes()
        }
    }

    fun loadJokesWithDelay() {
        viewModelScope.launch {
            delay(2000)
            _jokesFlow.emit(repository.loadJokesWithDelay())
        }
    }

    fun loadMoreJokes() {
        viewModelScope.launch(exceptionHandler) {
            mutableProgressLiveData.postValue(true)
            val response : JokeApiResponse = RetrofitInstance.api.getJokes()
            addJokes(response)
            mutableProgressLiveData.postValue(false)
        }
    }

    private fun addJokes(response: JokeApiResponse) {
        viewModelScope.launch(exceptionHandler) {
            val updatedList = _jokesFlow.first().toMutableList()
            val newJokes = emptyList<Joke>().toMutableList()
            response.jokes.forEach { newJoke ->
                val existingJoke = updatedList.find { it.id == newJoke.id }
                if (existingJoke == null) {
                    newJokes.add(newJoke)
                }
            }
            repository.addJokes(newJokes)
        }

    }

    private fun handleError(error: String) {
        _error.value = error
    }
}