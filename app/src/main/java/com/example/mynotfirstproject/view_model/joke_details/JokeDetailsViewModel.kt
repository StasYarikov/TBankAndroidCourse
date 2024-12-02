package com.example.mynotfirstproject.view_model.joke_details

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeApiResponse
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeRepository
import com.example.mynotfirstproject.data.api.RetrofitInstance
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class JokeDetailsViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _selectedJokeLiveData = MutableStateFlow<Joke?>(null)
    val selectedJoke: StateFlow<Joke?> = _selectedJokeLiveData

    private val mutableProgressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean> = mutableProgressLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun setJokePosition(position: Int) {
        viewModelScope.launch(exceptionHandler) {
            if (position == -1) {
                handleError("Invalid joke data!")
            } else {
                repository.getJokes().collect { jokes ->
                    if (position in jokes.indices) {
                        val item = jokes[position]
                        _selectedJokeLiveData.emit(item)
                    } else {
                        handleError("Position out of bounds!")
                    }
                }
            }
        }
    }

    fun deleteJoke() {
        viewModelScope.launch(exceptionHandler) {
            mutableProgressLiveData.postValue(true)
            repository.deleteJoke(selectedJoke.value!!.id)
            mutableProgressLiveData.postValue(false)
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable.toString())
        mutableProgressLiveData.postValue(false)
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}