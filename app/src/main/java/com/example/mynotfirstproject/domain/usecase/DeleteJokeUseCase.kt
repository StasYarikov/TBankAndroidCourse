package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.domain.repository.JokesRepository

class DeleteJokeUseCase(
    private val jokesRepository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
) {

    suspend operator fun invoke(jokeId: Int) {
        jokesRepository.deleteJoke(jokeId)
    }
}