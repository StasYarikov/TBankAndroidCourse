package com.example.mynotfirstproject.presentation.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.presentation.add_joke.AddJokeViewModel
import com.example.mynotfirstproject.presentation.joke_details.JokeDetailsViewModel
import com.example.mynotfirstproject.presentation.jokes_list.JokeListViewModel

class JokesViewModelFactory(private val repository: JokeRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokeListViewModel::class.java) -> {
                JokeListViewModel(repository) as T
            }
            modelClass.isAssignableFrom(JokeDetailsViewModel::class.java) -> {
                JokeDetailsViewModel(repository) as T
            }
            modelClass.isAssignableFrom(AddJokeViewModel::class.java) -> {
                AddJokeViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}