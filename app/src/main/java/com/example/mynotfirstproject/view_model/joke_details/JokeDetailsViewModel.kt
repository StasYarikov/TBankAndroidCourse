package com.example.mynotfirstproject.view_model.joke_details

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeRepository

class JokeDetailsViewModel(private val repository: JokeRepository) : ViewModel() {

    val selectedJoke: LiveData<Joke> = repository.getSelectedJoke()

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun setJokePosition(position: Int) {
        if (position == -1) {
            handleError("Invalid joke data!")
        } else {
            val item = repository.getJokes().value?.get(position)
            if (item != null) {
                repository.selectJoke(item)
            } else {
                handleError("Joke not found!")
            }
        }
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}