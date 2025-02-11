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
import dagger.Provides
import javax.inject.Inject
import javax.inject.Provider

class JokesViewModelFactory @Inject constructor(
    private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return creators[modelClass]?.get() as? T
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}