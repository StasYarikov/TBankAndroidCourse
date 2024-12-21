package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.domain.repository.JokesRepository
import com.example.mynotfirstproject.presentation.uientity.JokeUI

class AddJokesUseCase(
    private val jokesRepository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
) {

    suspend operator fun invoke(jokes: List<JokeUI>) {
        jokesRepository.addJokes(jokes.map {
            jokeUIJokeItemMapper.mapToJokeItemFromJokeUI(it)
        })
    }
}