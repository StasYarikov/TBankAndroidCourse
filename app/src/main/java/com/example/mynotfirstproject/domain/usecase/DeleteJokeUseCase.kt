package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.domain.repository.JokesRepository

class DeleteJokeUseCase(private val jokesRepository: JokesRepository) {

    suspend operator fun invoke(jokeId: Int) {
        jokesRepository.deleteJoke(jokeId)
    }
}