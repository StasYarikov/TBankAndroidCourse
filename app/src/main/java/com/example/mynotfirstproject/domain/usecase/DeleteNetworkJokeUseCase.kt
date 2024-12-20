package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.domain.repository.JokesRepository

class DeleteNetworkJokeUseCase(
    private val jokesRepository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
) {

    suspend operator fun invoke(jokeId: Int) {
        jokesRepository.deleteNetworkJoke(jokeId)
    }
}