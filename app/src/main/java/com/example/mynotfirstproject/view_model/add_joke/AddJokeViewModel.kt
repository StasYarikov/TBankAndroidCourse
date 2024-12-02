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

        val currentJokes = repository.getJokes().value ?: emptyList()
        val uniqueId = (currentJokes.maxOfOrNull { it.id } ?: 0) + 1

        if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
            repository.addJoke(Joke(id = uniqueId,
                category = category,
                setup = question,
                delivery = answer,
                picture = JokeGenerator.generateRandomPicture(),
                label = "Local"))
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