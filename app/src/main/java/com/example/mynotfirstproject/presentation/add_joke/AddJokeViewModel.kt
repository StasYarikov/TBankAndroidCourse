package com.example.mynotfirstproject.presentation.add_joke

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.jokeGenerator.JokeGeneratorImpl
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.domain.usecase.AddJokeUseCase
import com.example.mynotfirstproject.domain.usecase.GetJokesUseCase
import com.example.mynotfirstproject.presentation.uientity.JokeUI
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
            max(acc, joke.id)
        }
        val uniqueId = maxId + 1
        if (category.isNotBlank() && question.isNotBlank() && answer.isNotBlank()) {
            viewModelScope.launch {
                addJokeUseCase(
                    JokeUI(
                        id = uniqueId,
                        category = category,
                        setup = question,
                        delivery = answer,
                        picture = JokeGeneratorImpl.generateRandomPicture(),
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