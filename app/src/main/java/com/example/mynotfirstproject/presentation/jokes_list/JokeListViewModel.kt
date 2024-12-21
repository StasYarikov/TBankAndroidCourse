package com.example.mynotfirstproject.presentation.jokes_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotfirstproject.data.entity.JokeApiResponse
import com.example.mynotfirstproject.data.entity.Jokes
import com.example.mynotfirstproject.domain.usecase.AddJokesUseCase
import com.example.mynotfirstproject.domain.usecase.DeleteAllJokesUseCase
import com.example.mynotfirstproject.domain.usecase.GenerateJokesUseCase
import com.example.mynotfirstproject.domain.usecase.GetCacheJokesUseCase
import com.example.mynotfirstproject.domain.usecase.GetJokesUseCase
import com.example.mynotfirstproject.domain.usecase.LoadMoreJokesUseCase
import com.example.mynotfirstproject.presentation.uientity.JokeUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class JokeListViewModel @Inject constructor(
    private val getJokesUseCase: GetJokesUseCase,
    private val getCacheJokesUseCase: GetCacheJokesUseCase,
    private val generateJokesUseCase: GenerateJokesUseCase,
    private val loadMoreJokesUseCase: LoadMoreJokesUseCase,
    private val deleteAllJokesUseCase: DeleteAllJokesUseCase,
    private val addJokesUseCase: AddJokesUseCase
): ViewModel() {

    private val _jokesFlow = MutableStateFlow<List<JokeUI>>(emptyList())
    val jokesFlow: StateFlow<List<JokeUI>> get() = _jokesFlow

    private val mutableProgressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean> = mutableProgressLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    var loadingProcess: Boolean = false

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            val currentList = _jokesFlow.value.toMutableList()
            val cacheJokes = getCacheJokesUseCase()
            cacheJokes.forEach { newJoke ->
                val existingJoke : JokeUI? = currentList.find { it.id == newJoke.id }
                if (existingJoke == null) {
                    currentList.add(newJoke)
                }
            }
            _jokesFlow.value = currentList
            mutableProgressLiveData.postValue(false)
            handleError(throwable.toString())
        }
    }

    fun generateJokes() {
        viewModelScope.launch {
            generateJokesUseCase()
        }
    }

    fun loadJokesWithDelay() {
        viewModelScope.launch {
            _jokesFlow.value = getJokesUseCase()
        }
    }

    suspend fun loadMoreJokes() {
        mutableProgressLiveData.postValue(true)
        val response: List<JokeUI> = loadMoreJokesUseCase()
        addJokes(response)
        mutableProgressLiveData.postValue(false)
    }

    suspend fun deleteAllJokes() {
        deleteAllJokesUseCase()
        _jokesFlow.value = emptyList()
    }

    private suspend fun addJokes(response: List<JokeUI>) {
        val updatedList = _jokesFlow.first().toMutableList()
        val newNetworkJokes = emptyList<JokeUI>().toMutableList()
        response.forEach { newJoke ->
            val existingJoke = updatedList.find {
                it.id == newJoke.id
            }
            if (existingJoke == null) {
                newNetworkJokes.add(newJoke)
            }
        }
        addJokesUseCase(newNetworkJokes)
        val currentList = _jokesFlow.value.toMutableList()
        currentList.addAll(newNetworkJokes)
        _jokesFlow.value = currentList
    }

    private fun handleError(error: String) {
        _error.value = error
    }

    suspend fun updateNetworkJokes() {
        _jokesFlow.value = getCacheJokesUseCase()
    }
}