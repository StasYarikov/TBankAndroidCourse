package com.example.mynotfirstproject.presentation.joke_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.domain.repository.JokesRepository
import com.example.mynotfirstproject.domain.usecase.DeleteJokeUseCase
import com.example.mynotfirstproject.domain.usecase.DeleteNetworkJokeUseCase
import com.example.mynotfirstproject.domain.usecase.GetJokeByIdUseCase
import com.example.mynotfirstproject.presentation.uientity.JokeUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class JokeDetailsViewModel @Inject constructor(
    private val getJokeByIdUseCase: GetJokeByIdUseCase,
    private val deleteJokeUseCase: DeleteJokeUseCase,
    private val deleteNetworkJokeUseCase: DeleteNetworkJokeUseCase,
) : ViewModel() {

    private val _selectedJokesLiveData = MutableStateFlow<JokeUI?>(null)
    val selectedJokes: StateFlow<JokeUI?> = _selectedJokesLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    suspend fun setJokePosition(position: Int) {
        if (position == -1) {
            handleError("Invalid joke data!")
        } else {
            _selectedJokesLiveData.emit(getJokeByIdUseCase(position))
        }
    }

    suspend fun deleteJoke() {
        _selectedJokesLiveData.value?.let { deleteJokeUseCase(it.id) }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable.toString())
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}