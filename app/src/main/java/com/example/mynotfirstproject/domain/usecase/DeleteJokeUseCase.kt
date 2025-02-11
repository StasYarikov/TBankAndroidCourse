package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.presentation.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.domain.repository.JokesRepository
import javax.inject.Inject

class DeleteJokeUseCase @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
) {

    suspend operator fun invoke(jokeId: Int) {
        jokesRepository.deleteJoke(jokeId)
    }
}