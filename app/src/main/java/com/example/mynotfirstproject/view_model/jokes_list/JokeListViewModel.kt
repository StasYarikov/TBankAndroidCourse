package com.example.mynotfirstproject.view_model.jokes_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.NetworkJokes
import com.example.mynotfirstproject.data.JokeApiResponse
import com.example.mynotfirstproject.data.JokeRepository
import com.example.mynotfirstproject.data.Jokes
import com.example.mynotfirstproject.data.api.RetrofitInstance
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class JokeListViewModel(
    private val repository: JokeRepository
): ViewModel() {

    private val _jokesFlow = MutableStateFlow<List<Jokes>>(emptyList())
    val jokesFlow: StateFlow<List<Jokes>> get() = _jokesFlow

    private val _networkJokesFlow = MutableStateFlow<List<NetworkJokes>>(emptyList())
    val networkJokesFlow: StateFlow<List<NetworkJokes>> get() = _networkJokesFlow

    private val mutableProgressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean> = mutableProgressLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    var loadingProcess: Boolean = false

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            Log.d("Checking", "check")
            val currentList = _networkJokesFlow.value.toMutableList()
            val cacheJokes = repository.getCacheJokes()
            cacheJokes.forEach { newJoke ->
                val existingJoke = currentList.find { it.id == newJoke.id }
                if (existingJoke == null) {
                    currentList.add(newJoke)
                }
            }
            _networkJokesFlow.value = currentList
            mutableProgressLiveData.postValue(false)
            handleError(throwable.toString())
        }
    }

    fun generateJokes() {
        viewModelScope.launch {
            repository.generateJokes()
        }
    }

    fun loadJokesWithDelay() {
        viewModelScope.launch {
            repository.getJokes().collect {
                _jokesFlow.value = it
            }
        }
    }

    suspend fun loadMoreJokes() {
        Log.d("Checking", _networkJokesFlow.value.toString())
        mutableProgressLiveData.postValue(true)
        val response: JokeApiResponse = RetrofitInstance.api.getJokes()
        addJokes(response)
        mutableProgressLiveData.postValue(false)
    }

    suspend fun deleteAllJokes() {
        repository.deleteAllJokes()
        _jokesFlow.value = emptyList()
        _networkJokesFlow.value = emptyList()
    }

    private suspend fun addJokes(response: JokeApiResponse) {
        val updatedList = _networkJokesFlow.first().toMutableList()
        val newNetworkJokes = emptyList<NetworkJokes>().toMutableList()
        response.networkJokes.forEach { newJoke ->
            val existingJoke = updatedList.find { it.id == newJoke.id }
            if (existingJoke == null) {
                newNetworkJokes.add(newJoke)
            }
        }
        repository.addJokes(newNetworkJokes)
        val currentList = _networkJokesFlow.value.toMutableList()
        currentList.addAll(newNetworkJokes)
        _networkJokesFlow.value = currentList
    }

    private fun handleError(error: String) {
        _error.value = error
    }

    fun resetNetworkJokes() {
        _networkJokesFlow.value = emptyList()
    }

    suspend fun updateNetworkJokes() {
        if (_networkJokesFlow.value.isNotEmpty()) {
            _networkJokesFlow.value = repository.getCacheJokes()
        }
    }
}