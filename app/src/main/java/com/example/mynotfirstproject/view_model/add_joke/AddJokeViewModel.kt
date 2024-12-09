package com.example.mynotfirstproject.view_model.add_joke

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.NetworkJokes
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeRepository
import com.example.mynotfirstproject.data.Jokes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddJokeViewModel(private val repository: JokeRepository) : ViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    suspend fun addJoke(category: String, question: String, answer: String,) : String {
        var result = "Error"
        Log.d("Checking", "It works")
        val currentJokes = repository.getJokes().first()
        val uniqueId = (currentJokes.maxOfOrNull { it.id } ?: 0) + 1
        if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
            viewModelScope.launch {
                repository.addJoke(
                    Jokes(
                        id = uniqueId,
                        category = category,
                        setup = question,
                        delivery = answer,
                        picture = JokeGenerator.generateRandomPicture(),
                        label = "Local"
                    )
                )
            }
            result = "Success"
        } else {
            handleError("Заполните все поля!")
        }
        return result
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}