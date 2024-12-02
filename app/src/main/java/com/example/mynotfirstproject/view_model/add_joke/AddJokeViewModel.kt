package com.example.mynotfirstproject.view_model.add_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeRepository

class AddJokeViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun addJoke(category: String, question: String, answer: String,) : String {
        if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
            repository.addJoke(Joke(category = category, setup = question, delivery = answer, picture = JokeGenerator.generateRandomPicture()))
            return "OK"
        } else {
            handleError("Заполните все поля!")
            return "Error"
        }
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}