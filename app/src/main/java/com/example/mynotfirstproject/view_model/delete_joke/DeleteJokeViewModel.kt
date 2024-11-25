package com.example.mynotfirstproject.view_model.delete_joke

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

class DeleteJokeViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun deleteJoke(jokeNumber: String) : String {
        if (jokeNumber.isNotBlank()) {
            try {
                val jokeId = jokeNumber.toInt()
                if (jokeId > 0) {
                    repository.deleteJoke(jokeNumber.toInt() - 1)
                return "OK"
                }
                else {
                    handleError("Введены некорректные данные!")
                    return "Error"
                }
            } catch (e: NumberFormatException) {
                handleError("Введены некорректные данные!")
                return "Error"
            }
        } else {
            handleError("Заполните все поля!")
            return "Error"
        }
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}