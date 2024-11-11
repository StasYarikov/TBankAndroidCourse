package com.example.mynotfirstproject.view_model.joke_details

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotfirstproject.R
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.databinding.ActivityJokeDetailsBinding

class JokeDetailsViewModel: ViewModel() {

    private val _joke = MutableLiveData<Joke>()
    val joke: LiveData<Joke> = _joke

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val generator = JokeGenerator

    fun setJokePosition(position: Int) {
        if (position == -1) {
            handleError("Invalid joke data!")
        } else {
            val item = generator.data.getOrNull(position)
            if (item != null) {
                _joke.value = item
            } else {
                handleError("Joke not found!")
            }
        }
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}