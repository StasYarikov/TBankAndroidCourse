package com.example.mynotfirstproject.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.data.JokeRepository
import com.example.mynotfirstproject.view_model.add_joke.AddJokeViewModel
import com.example.mynotfirstproject.view_model.joke_details.JokeDetailsViewModel
import com.example.mynotfirstproject.view_model.jokes_list.JokeListViewModel

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