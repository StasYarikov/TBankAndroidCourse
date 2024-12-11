package com.example.mynotfirstproject.presentation.add_joke

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.domain.entity.JokeTypes
import com.example.mynotfirstproject.domain.repository.JokesRepository
import com.example.mynotfirstproject.domain.usecase.AddJokeUseCase
import com.example.mynotfirstproject.domain.usecase.GetJokeByIdUseCase
import com.example.mynotfirstproject.domain.usecase.GetJokesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.math.max

class AddJokeViewModel(
    private val getJokesUseCase: GetJokesUseCase,
    private val addJokeUseCase: AddJokeUseCase
) : ViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    suspend fun addJoke(category: String, question: String, answer: String,) : String {
        var result = "Error"
        val currentJokes = getJokesUseCase()
        val maxId = currentJokes.fold(-1) { acc, joke ->
            when (joke) {
                is JokeTypes.MyJokes -> max(acc, joke.data.id)
                is JokeTypes.JokesFromNetwork -> max(acc, joke.data.id)
            }
        }
        val uniqueId = maxId + 1
        if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
            viewModelScope.launch {
                addJokeUseCase(
                    JokeTypes.MyJokes(Jokes(
                        id = uniqueId,
                        category = category,
                        setup = question,
                        delivery = answer,
                        picture = JokeGenerator.generateRandomPicture(),
                        label = "Local"
                    ))
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