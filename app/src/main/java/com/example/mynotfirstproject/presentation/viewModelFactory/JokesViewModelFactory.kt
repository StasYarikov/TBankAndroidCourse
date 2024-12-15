package com.example.mynotfirstproject.presentation.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.data.repository.JokeRepository
import com.example.mynotfirstproject.domain.mapper.JokeUIJokeItemMapper
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

class JokesViewModelFactory(
    private val repository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        val getJokesUseCase = GetJokesUseCase(repository, jokeUIJokeItemMapper)
        val getCacheJokesUseCase = GetCacheJokesUseCase(repository, jokeUIJokeItemMapper)
        val generateJokesUseCase = GenerateJokesUseCase(repository, jokeUIJokeItemMapper)
        val loadMoreJokesUseCase = LoadMoreJokesUseCase(repository, jokeUIJokeItemMapper)
        val deleteAllJokesUseCase = DeleteAllJokesUseCase(repository, jokeUIJokeItemMapper)
        val addJokesUseCase = AddJokesUseCase(repository, jokeUIJokeItemMapper)
        val addJokeUseCase = AddJokeUseCase(repository, jokeUIJokeItemMapper)
        val getJokeByIdUseCase = GetJokeByIdUseCase(repository, jokeUIJokeItemMapper)
        val deleteJokeUseCase = DeleteJokeUseCase(repository, jokeUIJokeItemMapper)
        val deleteNetworkJokeUseCase = DeleteNetworkJokeUseCase(repository, jokeUIJokeItemMapper)

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