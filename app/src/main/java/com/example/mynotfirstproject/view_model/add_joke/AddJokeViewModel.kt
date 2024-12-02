package com.example.mynotfirstproject.view_model.add_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeRepository
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

class AddJokeViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun addJoke(category: String, question: String, answer: String,) : String {
        var result = "Error"
        viewModelScope.launch {
            val currentJokes = repository.getJokes().last()
            val uniqueId = (currentJokes.maxOfOrNull { it.id } ?: 0) + 1
            if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
                repository.addJoke(Joke(id = uniqueId,
                    category = category,
                    setup = question,
                    delivery = answer,
                    picture = JokeGenerator.generateRandomPicture(),
                    label = "Local"))
                result = "Error"
            } else {
                handleError("Заполните все поля!")
            }
        }
        return result
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}