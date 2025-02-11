package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.mapper.JokeUIJokeItemMapper
import com.example.mynotfirstproject.domain.repository.JokesRepository
import javax.inject.Inject

class DeleteAllJokesUseCase @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val jokeUIJokeItemMapper: JokeUIJokeItemMapper,
) {

    suspend operator fun invoke() {
        jokesRepository.deleteAllJokes()
    }
}