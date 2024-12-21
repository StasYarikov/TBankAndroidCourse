package com.example.mynotfirstproject.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mynotfirstproject.di.ViewModelKey
import com.example.mynotfirstproject.presentation.add_joke.AddJokeViewModel
import com.example.mynotfirstproject.presentation.joke_details.JokeDetailsViewModel
import com.example.mynotfirstproject.presentation.jokes_list.JokeListViewModel
import com.example.mynotfirstproject.presentation.viewModelFactory.JokesViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface PresentationModule {

    @Binds
    fun bindViewModelFactory(factory: JokesViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(JokeListViewModel::class)
    fun bindJokeListViewModel(viewModel: JokeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(JokeDetailsViewModel::class)
    fun bindJokeDetailsViewModel(viewModel: JokeDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddJokeViewModel::class)
    fun bindAddJokeViewModel(viewModel: AddJokeViewModel): ViewModel
}