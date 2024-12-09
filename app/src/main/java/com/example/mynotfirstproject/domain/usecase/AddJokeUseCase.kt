package com.example.mynotfirstproject.domain.usecase

import com.example.mynotfirstproject.domain.entity.JokeItem
import com.example.mynotfirstproject.domain.repository.JokesRepository

class AddJokeUseCase(private val jokesRepository: JokesRepository) {

    suspend operator fun invoke(jokeItem: JokeItem) {
        jokesRepository.addJoke(jokeItem)
    }
}