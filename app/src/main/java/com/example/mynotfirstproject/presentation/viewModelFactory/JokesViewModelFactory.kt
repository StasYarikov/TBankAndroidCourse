package com.example.mynotfirstproject.presentation.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.domain.repository.JokesRepository
import com.example.mynotfirstproject.domain.usecase.AddJokeUseCase
import com.example.mynotfirstproject.domain.usecase.AddJokesUseCase
import com.example.mynotfirstproject.domain.usecase.DeleteAllJokesUseCase
import com.example.mynotfirstproject.domain.usecase.DeleteJokeUseCase
import com.example.mynotfirstproject.domain.usecase.DeleteNetworkJokeUseCase
import com.example.mynotfirstproject.domain.usecase.GenerateJokesUseCase
import com.example.mynotfirstproject.domain.usecase.GetCacheJokesUseCase
import com.example.mynotfirstproject.domain.usecase.GetJokeByIdUseCase
import com.example.mynotfirstproject.domain.usecase.GetJokesUseCase
import com.example.mynotfirstproject.domain.usecase.LoadMoreJokesUseCase
import com.example.mynotfirstproject.presentation.add_joke.AddJokeViewModel
import com.example.mynotfirstproject.presentation.joke_details.JokeDetailsViewModel
import com.example.mynotfirstproject.presentation.jokes_list.JokeListViewModel

class JokesViewModelFactory(private val repository: JokesRepository): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        val getJokesUseCase = GetJokesUseCase(repository)
        val getCacheJokesUseCase = GetCacheJokesUseCase(repository)
        val generateJokesUseCase = GenerateJokesUseCase(repository)
        val loadMoreJokesUseCase = LoadMoreJokesUseCase(repository)
        val deleteAllJokesUseCase = DeleteAllJokesUseCase(repository)
        val addJokesUseCase = AddJokesUseCase(repository)
        val addJokeUseCase = AddJokeUseCase(repository)
        val getJokeByIdUseCase = GetJokeByIdUseCase(repository)
        val deleteJokeUseCase = DeleteJokeUseCase(repository)
        val deleteNetworkJokeUseCase = DeleteNetworkJokeUseCase(repository)

        return when {
            modelClass.isAssignableFrom(JokeListViewModel::class.java) -> {
                JokeListViewModel(
                    getJokesUseCase,
                    getCacheJokesUseCase,
                    generateJokesUseCase,
                    loadMoreJokesUseCase,
                    deleteAllJokesUseCase,
                    addJokesUseCase,
                ) as T
            }
            modelClass.isAssignableFrom(JokeDetailsViewModel::class.java) -> {
                JokeDetailsViewModel(
                    getJokeByIdUseCase,
                    deleteJokeUseCase,
                    deleteNetworkJokeUseCase,
                ) as T
            }
            modelClass.isAssignableFrom(AddJokeViewModel::class.java) -> {
                AddJokeViewModel(
                    getJokesUseCase,
                    addJokeUseCase,
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}