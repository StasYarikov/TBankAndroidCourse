package com.example.mynotfirstproject.view_model.joke_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.NetworkJokes
import com.example.mynotfirstproject.data.JokeRepository
import com.example.mynotfirstproject.data.JokeTypes
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JokeDetailsViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _selectedJokesLiveData = MutableStateFlow<JokeTypes?>(null)
    val selectedJokes: StateFlow<JokeTypes?> = _selectedJokesLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    suspend fun setJokePosition(position: Int) {
        if (position == -1) {
            handleError("Invalid joke data!")
        } else {
            _selectedJokesLiveData.emit(repository.getJokeById(position))
        }
    }

    suspend fun deleteJoke() {
        when {
            selectedJokes.value is JokeTypes.MyJokes -> repository.deleteJoke((selectedJokes.value as JokeTypes.MyJokes).data.id)
            selectedJokes.value is JokeTypes.JokesFromNetwork -> repository.deleteNetworkJoke((selectedJokes.value as JokeTypes.JokesFromNetwork).data.id)
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable.toString())
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}