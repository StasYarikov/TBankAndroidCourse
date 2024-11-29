package com.example.mynotfirstproject.view_model.jokes_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotfirstproject.data.Joke
import com.example.mynotfirstproject.data.JokeApiResponse
import com.example.mynotfirstproject.data.JokeGenerator
import com.example.mynotfirstproject.data.JokeRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JokeListViewModel(
    private val repository: JokeRepository
): ViewModel() {

    val jokes: LiveData<List<Joke>> = repository.getJokes()

    private val mutableProgressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val progressLiveData: LiveData<Boolean> = mutableProgressLiveData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable.toString())
        mutableProgressLiveData.postValue(false)
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager.itemCount
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

            if (totalItemCount == lastVisibleItemPosition + 1) {
                loadMoreJokes()
            }
        }
    }

    fun generateJokes() {
        repository.generateJokes()
    }

    fun loadJokesWithDelay() {
        viewModelScope.launch {
            delay(2000)
            repository.loadJokesWithDelay()
        }
    }

    fun loadMoreJokes() {
        viewModelScope.launch(exceptionHandler) {
            mutableProgressLiveData.postValue(true)
            val response : JokeApiResponse = RetrofitInstance.api.getJokes()
            repository.addJokes(response)
            mutableProgressLiveData.postValue(false)
        }
    }

    private fun handleError(error: String) {
        _error.value = error
    }
}