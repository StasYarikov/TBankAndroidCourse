package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeTypes
import com.example.mynotfirstproject.domain.repository.JokesRepository

class DeleteNetworkJokeUseCase(private val jokesRepository: JokesRepository) {

    suspend operator fun invoke(jokeId: Int) {
        jokesRepository.deleteNetworkJoke(jokeId)
    }
}