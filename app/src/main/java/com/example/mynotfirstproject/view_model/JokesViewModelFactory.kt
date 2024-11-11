package com.example.mynotfirstproject.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.view_model.joke_details.JokeDetailsViewModel
import com.example.mynotfirstproject.view_model.jokes_list.JokeListViewModel

class JokesViewModelFactory: ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(JokeListViewModel::class.java) -> {
                JokeListViewModel() as T
            }
            modelClass.isAssignableFrom(JokeDetailsViewModel::class.java) -> {
                JokeDetailsViewModel() as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}